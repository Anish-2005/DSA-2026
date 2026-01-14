//3454. Separate Squares II
//You are given a 2D integer array squares. Each 
// squares[i] = [xi, yi, li] represents the coordinates of 
// the bottom-left point and the side length of a square parallel to the x-axis.

//Find the minimum y-coordinate value of a horizontal line 
// such that the total area covered by squares above the line equals the total area covered by squares below the line.

//Answers within 10-5 of the actual answer will be accepted.

//Note: Squares may overlap. Overlapping areas should be counted 
// only once in this version.

import java.util.*;

class Solution {
    static class Event {
        long y, x1, x2;
        int t;
        Event(long y, long x1, long x2, int t) {
            this.y = y;
            this.x1 = x1;
            this.x2 = x2;
            this.t = t;
        }
    }

    static class SegTree {
        int n;
        long[] xs, len;
        int[] cnt;

        SegTree(long[] xs) {
            this.xs = xs;
            n = xs.length - 1;
            len = new long[4 * n];
            cnt = new int[4 * n];
        }

        void update(int i, int l, int r, int ql, int qr, int v) {
            if (qr <= l || r <= ql) return;
            if (ql <= l && r <= qr) cnt[i] += v;
            else {
                int m = (l + r) >>> 1;
                update(i << 1, l, m, ql, qr, v);
                update(i << 1 | 1, m, r, ql, qr, v);
            }
            if (cnt[i] > 0) len[i] = xs[r] - xs[l];
            else if (l + 1 == r) len[i] = 0;
            else len[i] = len[i << 1] + len[i << 1 | 1];
        }

        void update(int l, int r, int v) {
            update(1, 0, n, l, r, v);
        }

        long query() {
            return len[1];
        }
    }

    public double separateSquares(int[][] squares) {
        List<Event> events = new ArrayList<>();
        TreeSet<Long> xset = new TreeSet<>();

        for (int[] s : squares) {
            long x1 = s[0], y1 = s[1], l = s[2];
            long x2 = x1 + l, y2 = y1 + l;
            events.add(new Event(y1, x1, x2, 1));
            events.add(new Event(y2, x1, x2, -1));
            xset.add(x1);
            xset.add(x2);
        }

        long[] xs = xset.stream().mapToLong(v -> v).toArray();
        Map<Long, Integer> xid = new HashMap<>();
        for (int i = 0; i < xs.length; i++) xid.put(xs[i], i);

        events.sort(Comparator.comparingLong(e -> e.y));
        SegTree st = new SegTree(xs);

        List<Long> yStart = new ArrayList<>();
        List<Long> width = new ArrayList<>();
        List<Double> pref = new ArrayList<>();

        long prevY = events.get(0).y;
        double area = 0;
        int i = 0;

        while (i < events.size()) {
            long curY = events.get(i).y;
            long w = st.query();
            if (curY > prevY) {
                area += (double) w * (curY - prevY);
                yStart.add(prevY);
                width.add(w);
                pref.add(area);
            }
            while (i < events.size() && events.get(i).y == curY) {
                Event e = events.get(i);
                st.update(xid.get(e.x1), xid.get(e.x2), e.t);
                i++;
            }
            prevY = curY;
        }

        double target = area / 2.0;

        int k = 0;
        while (k < pref.size() && pref.get(k) < target) k++;

        double before = k == 0 ? 0 : pref.get(k - 1);
        long w = width.get(k);

        if (w == 0) return yStart.get(k);
        return yStart.get(k) + (target - before) / w;
    }
}
