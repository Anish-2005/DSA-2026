const fs = require('fs');
const path = require('path');

module.exports = (req, res) => {
  try {
    const p = req.query.path || req.url.split('?path=')[1] || '';
    if (!p) {
      res.statusCode = 400;
      return res.end(JSON.stringify({ error: 'missing path parameter' }));
    }

    const base = path.resolve(process.cwd());
    // normalize incoming path (use forward slashes in API)
    const parts = p.split('/').filter(Boolean);
    const target = path.join(base, ...parts);

    if (!target.startsWith(base)) {
      res.statusCode = 400;
      return res.end(JSON.stringify({ error: 'invalid path' }));
    }

    fs.readFile(target, 'utf8', (err, data) => {
      if (err) {
        res.statusCode = 404;
        return res.end(JSON.stringify({ error: 'file not found' }));
      }
      res.setHeader('Content-Type', 'application/json');
      res.end(JSON.stringify({ content: data }));
    });
  } catch (err) {
    res.statusCode = 500;
    res.end(JSON.stringify({ error: err.message }));
  }
};
