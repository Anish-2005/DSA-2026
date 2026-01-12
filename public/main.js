/*
  Professional frontend logic
  - debounced search
  - persistent UI state
  - optimized rendering
  - theme-aware charts
*/

const state = {
  files: [],
  filtered: [],
  expandedGroups: new Set(),
  currentPath: null,
  chart: null
};

/* ---------------- utils ---------------- */

async function fetchJSON(url) {
  const res = await fetch(url);
  if (!res.ok) throw new Error(await res.text());
  return res.json();
}

function el(tag, className, text) {
  const e = document.createElement(tag);
  if (className) e.className = className;
  if (text !== undefined) e.textContent = text;
  return e;
}

function debounce(fn, delay = 250) {
  let t;
  return (...args) => {
    clearTimeout(t);
    t = setTimeout(() => fn(...args), delay);
  };
}

function parseDayName(name) {
  const m = /^Day(\d+)$/.exec(name);
  return m ? parseInt(m[1], 10) : null;
}

function sortGroupNames(arr) {
  return arr.slice().sort((a, b) => {
    const an = parseDayName(a);
    const bn = parseDayName(b);
    if (an !== null && bn !== null) return an - bn;
    if (an !== null) return -1;
    if (bn !== null) return 1;
    if (a === 'root' && b !== 'root') return -1;
    if (b === 'root' && a !== 'root') return 1;
    return a.localeCompare(b);
  });
}

/* ---------------- init ---------------- */

window.addEventListener("load", async () => {
  try {
    const data = await fetchJSON("/api/files");
    state.files = data.files;
    state.filtered = data.files;

    renderDayFilter(sortGroupNames(Object.keys(data.grouped)));
    renderGroups(state.filtered);

    const analysis = await fetchJSON("/api/analysis");
    renderAnalysis(analysis);

    // auto-open first file
    if (state.filtered.length) openFile(state.filtered[0].path);
  } catch (err) {
    console.error(err);
    alert("Failed to load data");
  }
});

/* ---------------- filters ---------------- */

function renderDayFilter(days) {
  const sel = document.getElementById("dayFilter");
  sel.innerHTML = `<option value="">All Days</option>`;
  sortGroupNames(days).forEach(d => sel.appendChild(el("option", null, d)));

  sel.onchange = applyFilters;
  document
    .getElementById("search")
    .addEventListener("input", debounce(applyFilters, 200));
}

function applyFilters() {
  const q = document.getElementById("search").value.toLowerCase().trim();
  const day = document.getElementById("dayFilter").value;

  state.filtered = state.files.filter(f =>
    (!day || f.day === day) &&
    (!q || (f.name + f.path).toLowerCase().includes(q))
  );

  renderGroups(state.filtered);
}

/* ---------------- sidebar ---------------- */

function renderGroups(files) {
  const container = document.getElementById("groups");
  container.innerHTML = "";

  const grouped = {};
  files.forEach(f => {
    const g = f.day || "root";
    (grouped[g] ??= []).push(f);
  });

  sortGroupNames(Object.keys(grouped)).forEach(group => {
    const box = el("div", "group-box");
    const header = el("div", "flex justify-between items-center");

    const title = el(
      "div",
      "font-medium",
      `${group} · ${grouped[group].length}`
    );

    const toggle = el("button", "text-xs text-sky-500", "Toggle");
    toggle.onclick = () => {
      state.expandedGroups.has(group)
        ? state.expandedGroups.delete(group)
        : state.expandedGroups.add(group);
      list.classList.toggle("hidden");
    };

    header.append(title, toggle);
    box.appendChild(header);

    const list = el(
      "div",
      "mt-2 space-y-1" +
        (state.expandedGroups.has(group) ? "" : " hidden")
    );

    grouped[group]
      .sort((a, b) => a.name.localeCompare(b.name))
      .forEach(file => {
        const item = el(
          "div",
          "file-item text-sm",
          `${file.name} (${file.lines})`
        );

        if (file.path === state.currentPath) {
          item.classList.add("selected");
        }

        item.onclick = () => {
          document
            .querySelectorAll(".file-item")
            .forEach(x => x.classList.remove("selected"));
          item.classList.add("selected");
          openFile(file.path);
        };

        list.appendChild(item);
      });

    box.appendChild(list);
    container.appendChild(box);
  });
}

/* ---------------- file viewer ---------------- */

async function openFile(path) {
  try {
    state.currentPath = path;
    const data = await fetchJSON(`/api/file?path=${encodeURIComponent(path)}`);

    document.getElementById("meta").textContent = path;
    document.getElementById("fileTitle").textContent =
      path.split("/").pop();

    const code = document.getElementById("codeBlock");
    code.textContent = data.content;
    hljs.highlightElement(code);

    // copy
    document.getElementById("copyBtn").onclick = async () => {
      await navigator.clipboard.writeText(data.content);
      flash("copyBtn", "Copied!");
    };

    // download
    const blob = new Blob([data.content], { type: "text/plain" });
    const url = URL.createObjectURL(blob);
    const dl = document.getElementById("downloadLink");
    dl.href = url;
    dl.download = path.split("/").pop();
  } catch (err) {
    console.error(err);
    alert("Failed to open file");
  }
}

function flash(id, text) {
  const btn = document.getElementById(id);
  const old = btn.textContent;
  btn.textContent = text;
  setTimeout(() => (btn.textContent = old), 1200);
}

/* ---------------- theme ---------------- */

(function initTheme() {
  const toggle = document.getElementById("themeToggle");
  const set = dark => {
    document.documentElement.classList.toggle("dark", dark);
    toggle.textContent = dark ? "Light" : "Dark";
  };

  set(localStorage.getItem("prefTheme") === "dark");

  toggle.onclick = () => {
    const dark = !document.documentElement.classList.contains("dark");
    localStorage.setItem("prefTheme", dark ? "dark" : "light");
    set(dark);
  };
})();

/* ---------------- analytics ---------------- */

function renderAnalysis(a) {
  const root = document.getElementById("analysis");
  root.innerHTML = `
    <div>Total files: ${a.totalFiles}</div>
    <div>Avg lines/file: ${a.avgLines}</div>
  `;

  const labels = sortGroupNames(Object.keys(a.perDay));
  const counts = labels.map(k => a.perDay[k].count);

  const ctx = document.getElementById("dayChart");

  if (state.chart) state.chart.destroy();

  state.chart = new Chart(ctx, {
    type: "bar",
    data: {
      labels,
      datasets: [{
        data: counts,
        backgroundColor: getComputedStyle(document.documentElement)
          .getPropertyValue("--accent") || "#2563eb"
      }]
    },
    options: {
      plugins: { legend: { display: false } },
      scales: {
        x: { ticks: { color: "#94a3b8" } },
        y: { ticks: { color: "#94a3b8" } }
      }
    }
  });

  root.appendChild(el("div", "mt-2 font-medium", "Longest files:"));
  const ol = el("ol", "list-decimal pl-5 text-sm");
  a.longest.forEach(f =>
    ol.appendChild(el("li", null, `${f.path} — ${f.lines}`))
  );
  root.appendChild(ol);
}
