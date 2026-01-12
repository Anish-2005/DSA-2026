const fs = require('fs');
const path = require('path');

function walk(dir, list) {
  let entries;
  try {
    entries = fs.readdirSync(dir, { withFileTypes: true });
  } catch (e) {
    return;
  }
  for (const e of entries) {
    const full = path.join(dir, e.name);
    if (e.isDirectory()) walk(full, list);
    else if (e.isFile() && full.endsWith('.java')) list.push(full);
  }
}

function collectAllJavaFiles() {
  const bases = [process.cwd(), path.resolve(__dirname, '..')];
  const seen = new Set();
  const all = [];
  for (const base of bases) {
    const list = [];
    walk(base, list);
    for (const fp of list) {
      const rel = path.relative(base, fp).split(path.sep).join('/');
      if (!seen.has(rel)) {
        seen.add(rel);
        all.push({ fp, base });
      }
    }
  }
  return all;
}

module.exports = (req, res) => {
  try {
    const publicAnalysis = path.join(process.cwd(), 'public', 'analysis.json');
    if (fs.existsSync(publicAnalysis)) {
      const data = JSON.parse(fs.readFileSync(publicAnalysis, 'utf8'));
      res.setHeader('Content-Type', 'application/json');
      return res.end(JSON.stringify(data));
    }

    const all = collectAllJavaFiles();

    const items = all.map(({ fp, base }) => {
      const rel = path.relative(base, fp).split(path.sep).join('/');
      const content = fs.readFileSync(fp, 'utf8');
      const lines = content.split(/\r?\n/).length;
      const day = rel.split('/')[0] || 'root';
      return { path: rel, lines, day };
    });

    const totalFiles = items.length;
    const avgLines = totalFiles ? Math.round(items.reduce((s, i) => s + i.lines, 0) / totalFiles) : 0;

    const perDay = {};
    items.forEach(i => {
      perDay[i.day] ??= { count: 0 };
      perDay[i.day].count++;
    });

    const longest = items.sort((a, b) => b.lines - a.lines).slice(0, 10);

    res.setHeader('Content-Type', 'application/json');
    res.end(JSON.stringify({ totalFiles, avgLines, perDay, longest }));
  } catch (err) {
    res.statusCode = 500;
    res.end(JSON.stringify({ error: err.message }));
  }
};
