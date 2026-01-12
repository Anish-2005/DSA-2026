const fs = require('fs');
const path = require('path');

module.exports = (req, res) => {
  try {
    const p = req.query.path || req.url.split('?path=')[1] || '';
    if (!p) {
      res.statusCode = 400;
      return res.end(JSON.stringify({ error: 'missing path parameter' }));
    }

    // try multiple base directories (cwd and function parent)
    const bases = [process.cwd(), path.resolve(__dirname, '..')];
    const parts = p.split('/').filter(Boolean);

    let found = false;
    for (const base of bases) {
      const target = path.join(base, ...parts);
      if (!target.startsWith(base)) continue;
      try {
        const data = fs.readFileSync(target, 'utf8');
        res.setHeader('Content-Type', 'application/json');
        res.end(JSON.stringify({ content: data }));
        found = true;
        break;
      } catch (e) {
        // try next base
      }
    }

    if (!found) {
      res.statusCode = 404;
      return res.end(JSON.stringify({ error: 'file not found' }));
    }
  } catch (err) {
    res.statusCode = 500;
    res.end(JSON.stringify({ error: err.message }));
  }
};
