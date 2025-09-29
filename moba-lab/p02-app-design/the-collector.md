# The Collector

## 1) Problem & Customer Hypothesis

### problem hypothesis

Collectors lack fast, flexible cataloging and live price tracking.

### customer hypothesis

Collectors want fast item capture, custom fields, and live prices.

---

## 2) Questions for Potential Customers

**Collection Workflow**

- How do you track your collection? (app, spreadsheet, paper)
- What’s the hardest part of adding new items?

**Valuation**

- Do you check live prices? Where?
- How accurate do prices need to be?

**Organization & Sharing**

- Do you use sets/folders or tags?
- Do you share lists for trades/sales?

**Must-Have Features**

- What would make you switch from your current method?

---

## 3) Discuss & Refine (key learnings we expect)

- **Speed > everything**: snap-to-add with OCR/barcode/visual ID is essential.
- **Custom schemas** per set are non-negotiable (fields differ by category).
- **Live pricing** must support **multiple sources** and show **provenance** (last sold, median, trend).
- **Search & filters** across custom fields need to be powerful and fast.
- **Share/export** for trades/insurance is a frequent job-to-be-done.
- **Trust** comes from transparent pricing sources + audit trail of edits.

---

## 4) Prototype Persona

**Name:** Lena Baumann
**Age:** 28
**Location:** Zürich
**Collection types:** Pokémon/TCG (~800 cards), limited vinyl (~120 records)
**Devices:** iPhone + iCloud; uses Google Sheets; eBay/Discogs/Cardmarket
**Goals:**

- Log items quickly after a purchase or trade
- Know **current value** of subsets (e.g., “Vintage holo set”)
- Avoid duplicates; prep trade lists for meetups
  **Frustrations:**
- Manual entry takes too long; photos don’t link to rows
- Values are stale; needs to open many tabs to verify prices
- Every category needs different fields; existing apps are too rigid
  **Behaviors:**
- Adds 5–10 items per week; batch-updates monthly
- Checks prices before trades; shares lists via link
  **Quote:** “If I can snap a card and get fields + price filled in, I’ll finally keep my catalog up to date.”

---

## 5) User Story for the Main Feature

**User Story**
As **Lena (a multi-category collector)**, I want to **snap a photo of an item and have the app auto-fill my set’s custom fields and current market price**, so that **adding to my catalog is fast and my valuations stay accurate**.

**Acceptance Criteria**

- From a set, tapping **“Add item”** opens camera immediately.
- After capture, the app shows:

    - Suggested **title**, **category**, **set/series**, **edition**, **condition/grade** (if detectable), **year**, **ID/serial** (if visible)
    - **Live price** with **source labels** (e.g., “eBay sold median, last 30 days (n=47)”)
    - **Confidence indicators** for each suggestion; ambiguous fields flagged for review
- The set’s **custom fields** (defined in the template) appear with suggested values prefilled when possible.
- User can **accept all** or edit any field before saving.
- Saving stores **photos**, **metadata**, **pricing snapshot**, and **source links**.
- If barcode is present, the app can **scan instead of photo**; if both, uses both to improve confidence.
- If no match found, user can **save partial** and mark “needs pricing.”
- Item appears in the set list instantly and in **Recent Adds**.

**Main Flow (related steps)**

1. Open “Pokémon Base Set (Holo)”
2. Tap **Add item** -> camera opens
3. Snap card -> on-device model/OCR runs; price sources queried
4. Review screen shows prefilled fields (Set, Card #, Edition, Condition, Year), price card with “eBay sold median CHF 85 (n=47), 30d”
5. Edit Condition to “Near Mint”, add Tag “For Trade”
6. Save -> toast “Added • Price snapshot saved • 1 alert available”
7. Item listed; tap to view details, sources, photos; optional export/share

---

## 6) Wireframes

![Wireframes](images/moba-Page%201.png)


## 7) Mockup

![Mockup](images/moba-Page%202.png)