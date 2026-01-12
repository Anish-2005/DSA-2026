const express = require('express');
const cors = require('cors');
const fs = require('fs');
const path = require('path');

const app = express();
app.use(cors());
app.use(express.json());

const BASE_DIR = path.join(__dirname);

function walkDir(dir, fileList = []) {
  const files = fs.readdirSync(dir, { withFileTypes: true });
  for (const f of files) {
    const full = path.join(dir, f.name);
    if (f.isDirectory()) {
      // skip node_modules and .git and public
      if (['node_modules', '.git', 'public'].includes(f.name)) continue;
      walkDir(full, fileList);
    } else if (f.isFile() && full.endsWith('.java')) {
      fileList.push(full);
    }
  }
  return fileList;
}

function fileMeta(fullPath) {
  const rel = path.relative(BASE_DIR, fullPath).replace(/\\/g, '/');
  const stat = fs.statSync(fullPath);
  const content = fs.readFileSync(fullPath, 'utf8');
  const lines = content.split(/\r?\n/).length;
  const parts = rel.split('/');
  const day = parts.length > 1 ? parts[0] : '';
  return {
    path: rel,
    name: path.basename(fullPath),
    day,
    size: stat.size,
    lines
  };
}

app.get('/api/files', (req, res) => {
  try {
    const files = walkDir(BASE_DIR).map(fileMeta);
    // group by day
    const grouped = {};
    for (const f of files) {
      const g = f.day || 'root';
      if (!grouped[g]) grouped[g] = [];
      grouped[g].push(f);
    }
    res.json({ files, grouped });
  } catch (err) {
    res.status(500).json({ error: String(err) });
  }
});

app.get('/api/file', (req, res) => {
  const rel = req.query.path;
  if (!rel) return res.status(400).json({ error: 'path query param required' });
  const full = path.join(BASE_DIR, rel);
  if (!full.startsWith(BASE_DIR)) return res.status(400).json({ error: 'invalid path' });
  if (!fs.existsSync(full)) return res.status(404).json({ error: 'not found' });
  const content = fs.readFileSync(full, 'utf8');
  res.json({ path: rel, content });
});

app.get('/api/analysis', (req, res) => {
  try {
    const files = walkDir(BASE_DIR).map(fileMeta);
    const perDay = {};
    let totalLines = 0;
    for (const f of files) {
      totalLines += f.lines;
      const g = f.day || 'root';
      perDay[g] = perDay[g] || { count: 0, lines: 0 };
      perDay[g].count += 1;
      perDay[g].lines += f.lines;
    }
    const avgLines = files.length ? Math.round(totalLines / files.length) : 0;
    const longest = files.slice().sort((a,b)=>b.lines-a.lines).slice(0,10);
    res.json({ totalFiles: files.length, avgLines, perDay, longest });
  } catch (err) {
    res.status(500).json({ error: String(err) });
  }
});

app.use('/', express.static(path.join(__dirname, 'public')));

const port = process.env.PORT || 3000;
app.listen(port, () => console.log(`DSA website running at http://localhost:${port}`));
