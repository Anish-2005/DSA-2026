const fs = require('fs');
const path = require('path');

function walk(dir, list) {
  const entries = fs.readdirSync(dir, { withFileTypes: true });
  for (const e of entries) {
    const full = path.join(dir, e.name);
    if (e.isDirectory()) walk(full, list);
    else if (e.isFile() && full.endsWith('.java')) list.push(full);
  }
}

module.exports = (req, res) => {
  try {
    const base = path.resolve(process.cwd());
    const all = [];
    walk(base, all);

    const items = all.map(fp => {
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
