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

    const files = all.map(fp => {
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
