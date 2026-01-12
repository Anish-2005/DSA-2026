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
    // prefer prebuilt index for serverless deployments
    const publicFiles = path.join(process.cwd(), 'public', 'files.json');
    if (fs.existsSync(publicFiles)) {
      const data = JSON.parse(fs.readFileSync(publicFiles, 'utf8'));
      res.setHeader('Content-Type', 'application/json');
      return res.end(JSON.stringify(data));
    }

    const all = collectAllJavaFiles();
    const files = all.map(({ fp, base }) => {
      const rel = path.relative(base, fp).split(path.sep).join('/');
      const name = path.basename(fp);
      const content = fs.readFileSync(fp, 'utf8');
      const lines = content.split(/\r?\n/).length;
      const parts = rel.split('/');
      const day = parts[0] || 'root';
      return { path: rel, name, lines, day };
    });

    const grouped = {};
    files.forEach(f => (grouped[f.day] ??= []).push(f));

    res.setHeader('Content-Type', 'application/json');
    res.end(JSON.stringify({ files, grouped }));
  } catch (err) {
    res.statusCode = 500;
    res.end(JSON.stringify({ error: err.message }));
  }
};
