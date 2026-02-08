# DSA Solutions Browser

![banner](https://img.shields.io/badge/DSA-Solutions-blue?style=for-the-badge)

An interactive local web app to browse, preview and get analytics for your solved DSA problems (Java). Beautiful, minimal UI with syntax highlighting and lightweight analytics.

Quick preview: open the app locally and explore a searchable list of problem files on the left, view code in the center pane, and see per-day metrics on the right.

---

## 🚀 Highlights

- Lightweight Node.js + Express static + API server
- Client-side UI with syntax highlighting (Highlight.js) and charts (Chart.js)
- Searchable, grouped file list (by day)
- Copy / Download quick actions for any file
- Theme toggle (dark / light) with persistent preference

---

## 🔧 Quickstart

1. Install dependencies

```bash
cd "c:\Users\ANISH\Documents\PROJECTS\DSA-2026"
npm install
```

2. Start server

```bash
npm start
```

3. Open in browser

Visit: http://localhost:3000

Tip: If the app doesn't start, check `server.js` for the configured port or run `PORT=3000 npm start` on Unix-like shells.

---

## 🎨 UI Walkthrough

- **Left — Problems**: grouped by day. Click a file to open it in the viewer.
- **Center — File Viewer**: syntax-highlighted code, responsive scrolling, copy & download buttons.
- **Right — Analytics**: bar chart of files per day, average lines per file, and list of longest files.

Screenshot placeholder

![screenshot](public/screenshot.png)

If you'd like a screenshot here, save a PNG at `public/screenshot.png` and this README will render it on GitHub.

---

## 📁 Project Structure

```
.
├─ server.js           # Express server + simple API
├─ package.json
├─ public/
│  ├─ index.html       # SPA UI
│  ├─ main.js          # client logic (search, render, fetch)
│  ├─ styles.css       # optional (most styles are inline)
│  └─ ...              # static assets
└─ Day*/               # your solved problems grouped by day
```

Key files:
- `server.js` — scans repo, returns `/api/files`, `/api/file?path=...`, `/api/analysis`.
- `public/index.html` — main UI markup and CSS.
- `public/main.js` — client-side logic (search, rendering, charting).

---

## 🔌 API (Local)

The app exposes a small JSON API used by the frontend (these are implemented in `server.js`):

- `GET /api/files` — list of files, grouped metadata (name, path, lines, day)
- `GET /api/file?path=<encodedPath>` — returns `{ content: string }` for the requested file
- `GET /api/analysis` — analytics summary: totals, per-day counts, longest files

Example: fetch a file with curl

```bash
curl "http://localhost:3000/api/file?path=$(node -e "console.log(encodeURIComponent('Day1/3477.java'))")"
```

---

## 🛠️ Customization & Tips

- To change the syntax theme: edit the `hljs-theme` link in `public/index.html` to any Highlight.js theme CDN URL.
- To change chart appearance: modify `public/main.js` where `new Chart(...)` is created.
- To extend scanning: edit `server.js` file discovery logic (file extensions, ignore rules).

Responsive layout tips:
- The UI uses flexbox; if you modify `.code-wrap`, keep `min-h-0`/`h-full` rules on parent containers so the code pane can scroll correctly.

---

## ⚙️ Development

1. Run with nodemon for live reload (install globally or dev-dependency):

```bash
npm i -g nodemon
nodemon server.js
```

2. Make frontend changes in `public/` and refresh the browser.

3. Add tests: this repo ships without tests by design — add unit tests for scanning or API behavior if desired.

---

## 🧭 Troubleshooting

- App shows blank page: open browser console to see client errors. Check network tab for failing `/api/*` calls.
- Files missing: ensure your `.java` files are in the repo folder and readable by the server process.
- Port already in use: change `PORT` env var or the value in `server.js`.

If you want, I can add a debug route that returns the scanned file list for quick inspection.

---

## 🤝 Contributing

Love improvements? Open issues or PRs. Good first contributions:
- Add tests for file scanning
- Add export of analytics (CSV)
- Improve UX with keyboard navigation

When opening a PR, please explain the change and keep commits focused.

---

## 📝 License

This project is provided as-is. Add a license (MIT/Apache/etc.) if you plan to share publicly.

---

Made with ❤️ — enjoy browsing your DSA progress!

---

## ☁️ Deploy to Vercel (fast)

You can deploy this repository to Vercel as a static frontend plus serverless API functions. I added example serverless endpoints under `api/` that mirror the local `server.js` behavior.

1. Install the Vercel CLI (optional but useful):

```bash
npm i -g vercel
```

2. Log in and deploy from your project root:

```bash
vercel login
vercel
```

Follow prompts (project name, scope). Vercel will detect the `api/` serverless functions and serve static files from `public/`.

3. (Alternative) Connect your GitHub/GitLab/Bitbucket repo in the Vercel dashboard and import the project. Use these settings when asked:

- Framework: `Other`
- Build Command: (leave empty)
- Output Directory: `public`

Notes & tips:
- The `api/` folder contains three serverless functions: `files.js`, `file.js`, `analysis.js`.
- The functions read the repository files that are deployed — ensure your `Day*/` folders are included in the repo you deploy.
- If you prefer a custom `vercel.json`, it's already included; it registers `@vercel/node` for `api/` and rewrites routes to `public/`.
- For large repositories, scanning at runtime may be slower; consider pre-building an index at build time and embedding it into `public/`.

If you want, I can also:
- Add a build-time script to generate a cached `files.json` to speed up serverless responses.
- Create a GitHub Action that deploys to Vercel automatically on push.
