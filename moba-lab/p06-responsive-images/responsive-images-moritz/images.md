# Responsive images - practical summary + quick templates

## 1) Conceptual bones (what the browser cares about)

- **`srcset` + descriptors** lists available image files and what they represent.
    - `w` descriptors (e.g. `480w`) = the file is intended for an image that *renders* that many **CSS pixels** wide.
    - `x` descriptors (e.g. `2x`) = density variants for a *fixed CSS size* (pick by DPR).
- **`sizes`** tells the browser *how many CSS pixels* the image will take in the layout (under different viewport
  conditions). Browser uses `sizes` + viewport + DPR to pick the best `srcset` candidate.
- **`<picture>`**: choose when you need different source *content* or formats (different crops/aspect or AVIF/WebP
  first, JPEG fallback). Otherwise use a plain `<img>` with `srcset`/`sizes`.
- The browser decision: 1) find first matching `<source>` (top→down), 2) build candidates from its `srcset`, 3) compute
  intended CSS width from `sizes`, 4) multiply by DPR → desired device pixels, 5) pick smallest `w` ≥ desired (or best
  `x` for density descriptors).

---

## 2) Which descriptor to use — rule of thumb

- **Use `w` (width descriptors) almost always** for content images that scale (articles, heroes, galleries).
    - More flexible and accurate: the browser considers layout width **and** DPR.
- **Use `x` (1x/2x/3x)** for small UI assets that always render at the same CSS size (icons, logos).
- Use **both** only for very specific needs; usually unnecessary.

---

## 3) `sizes` explained simply

`sizes` = “If the viewport matches media-query X, the image will take Y CSS pixels.”

Example:

```text
sizes="(max-width:600px) 90vw, (max-width:1200px) 80vw, 1200px"
```

Interpretation:

- viewport ≤ 600px → image ≈ 90% of viewport width (90vw)
- viewport ≤ 1200px → image ≈ 80% of viewport width
- otherwise → image ≈ 1200 CSS px

Browser computes: `intended CSS width × DPR` → chooses appropriate `srcset` item.

Rule of thumb: be honest — `sizes` should reflect your actual layout rules (or the browser will pick wrong).

---

## 4) Image file sizes — what to export

- The `480w`, `960w`, `1920w` values are **CSS px**. Export image files at (or slightly above) those real pixel widths.
- Common practical export set (covers most sites): **320, 480, 768, 960, 1280, 1600, 1920**.

    - Usually **3–4 sizes per image** is enough (e.g. 480 / 960 / 1920).
- Don’t overshoot: if your content area is max 1200px, you rarely need 3840px files.

Rule of thumb: 3 sizes per image (small/medium/large) + modern formats = sweet spot.

---

## 5) DPR (1x/2x/3x) — should you always do 3x?

- No. Typical devices:

    - laptops (Retina) → **~2x**
    - some phones → 3x
- Serving only x3 would waste bandwidth for many users.
- Recommendation: provide 1× and 2× (and 3× only for small fixed-size UI assets when necessary).

---

## 6) When to use `<picture>` vs `<img>`

- **Use `<img>` + `srcset`/`sizes`** when the same composition (crop/aspect) is used at all sizes — but different
  resolutions are needed.
- **Use `<picture>`** when:

    - You need different crops or aspect ratios by breakpoint (mobile crop vs desktop panorama), or
    - You want to prefer AVIF/WebP and fall back to JPEG/PNG.

---

## 7) Formats & accessibility

- Prefer modern formats **AVIF > WebP > JPEG/PNG** (use `<picture>` to serve modern first and fallback).
- Always include meaningful `alt` text.
- Add `width` and `height` attributes (reserve layout space → reduces CLS).
- Use `loading="lazy"` for non-critical offscreen images. Use `decoding="async"` if desired.

---

## 8) Quick step-by-step of the browser decision (short)

1. Check `<picture>` sources top→down; pick first `source` whose `media` (and `type`) matches.
2. Parse that source’s `srcset`. If entries use `w`: browser needs `sizes` to know layout CSS width.
3. Browser computes `display CSS width × DPR = desired device pixels`.
4. Pick smallest candidate whose `w` ≥ desired; if none, use largest available.
5. If `x` descriptors present, pick according to DPR.

---

## 9) Recommended templates (copy-paste)

Minimal, recommended for most responsive content images:

```html
<img
        src="images/photo-960.jpg"
        srcset="
    images/photo-480.jpg 480w,
    images/photo-960.jpg 960w,
    images/photo-1920.jpg 1920w"
        sizes="(max-width:600px) 90vw, (max-width:1200px) 80vw, 1200px"
        width="1200" height="800"
        alt="Short but descriptive alt text"
        loading="lazy"
        decoding="async" >
```

With modern formats (AVIF → WebP → JPEG fallback):

-> format-first method: (prefer over media-first)

```html

<picture >
    <source type="image/avif"
            srcset="images/photo-480.avif 480w, images/photo-960.avif 960w, images/photo-1920.avif 1920w"
            sizes="(max-width:600px) 90vw, (max-width:1200px) 80vw, 1200px" >
    <source type="image/webp"
            srcset="images/photo-480.webp 480w, images/photo-960.webp 960w, images/photo-1920.webp 1920w"
            sizes="(max-width:600px) 90vw, (max-width:1200px) 80vw, 1200px" >
    <img src="images/photo-960.jpg"
         srcset="images/photo-480.jpg 480w, images/photo-960.jpg 960w, images/photo-1920.jpg 1920w"
         sizes="(max-width:600px) 90vw, (max-width:1200px) 80vw, 1200px"
         width="1200" height="800"
         alt="Short but descriptive alt text"
         loading="lazy" >
</picture >
```

> Browser Flow: chooses the format first, then picks the correct size `max-width` and calculate the needed actual pixel width
> with the vw amount. Then it picks the best candidate from the srcset.

-> media-first method:

```html

<picture >
    <!-- desktop crop: larger panorama -->
    <source media="(min-width:1200px)"
            srcset="hero-desktop-2400.jpg 2400w"
            sizes="100vw" >
    <!-- tablet crop: different aspect -->
    <source media="(min-width:600px)"
            srcset="hero-tablet-1200.jpg 1200w"
            sizes="100vw" >
    <!-- mobile: density-based small file -->
    <source srcset="hero-mobile-1x.jpg 1x, hero-mobile-2x.jpg 2x" >
    <img src="hero-tablet-1200.jpg" alt="..." >
</picture >
```

Fixed-size UI asset (logo/icon) using DPR `x` descriptors:

```html
<img
        src="icons/logo@1x.png"
        srcset="icons/logo@2x.png 2x, icons/logo@3x.png 3x"
        width="120" height="40"
        alt="Site logo" >
```

---

## 10) Quick cheat-sheet / decision flow (one-liner rules)

- Same crop, responsive layout → **`img` + `w` descriptors + `sizes`**.
- Different crop/aspect or different format fallback → **`<picture>` + `<source>`**.
- Fixed CSS size UI asset → **`x` descriptors** (1x/2x/3x).
- Export ~**3 sizes** per image (small/medium/large) matching your layout breakpoints.
- Provide **1× and 2×** density coverage; add 3× only when needed.
- Always include `alt`, `width` & `height`, `loading="lazy"`.

