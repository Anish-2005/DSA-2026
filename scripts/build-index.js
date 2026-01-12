const fs = require('fs');
const path = require('path');

function walk(dir, list) {
  let entries;
  try {
    entries = fs.readdirSync(dir, { withFileTypes: true });
  } catch (e) { return; }
  for (const e of entries) {
    const full = path.join(dir, e.name);
    // skip node_modules and .vercel and public to avoid recursion
    if (e.isDirectory() && (e.name === 'node_modules' || e.name === '.vercel' || e.name === 'public')) continue;
    if (e.isDirectory()) walk(full, list);
    else if (e.isFile() && full.endsWith('.java')) list.push(full);
  }
}

function build() {
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
    return { path: rel, name, lines, day, content };
  });

  const grouped = {};
  files.forEach(f => (grouped[f.day] ??= []).push(f));

  const outDir = path.join(base, 'public');
  if (!fs.existsSync(outDir)) fs.mkdirSync(outDir, { recursive: true });

  fs.writeFileSync(path.join(outDir, 'files.json'), JSON.stringify({ files: files.map(f => ({ path: f.path, name: f.name, lines: f.lines, day: f.day })), grouped }, null, 2));

  const totalFiles = files.length;
  const avgLines = totalFiles ? Math.round(files.reduce((s, f) => s + f.lines, 0) / totalFiles) : 0;
  const perDay = {};
  files.forEach(f => (perDay[f.day] ??= { count: 0 }, perDay[f.day].count++));
  const longest = files.slice().sort((a,b) => b.lines - a.lines).slice(0, 10).map(f => ({ path: f.path, lines: f.lines }));

  fs.writeFileSync(path.join(outDir, 'analysis.json'), JSON.stringify({ totalFiles, avgLines, perDay, longest }, null, 2));

  // write individual file contents into public/files_contents.json for quick access
  const contents = {};
  files.forEach(f => contents[f.path] = f.content);
  fs.writeFileSync(path.join(outDir, 'files_contents.json'), JSON.stringify(contents, null, 2));

  console.log('Built public/files.json (', files.length, 'files )');
}

build();
