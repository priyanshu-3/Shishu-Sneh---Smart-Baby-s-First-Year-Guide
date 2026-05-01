# Shishu-Sneh - Smart Baby's First Year Guide

**Prepared by:** Priyanshu Mehra  
**USN:** 1VE22CS119  
**University:** Visvesvaraya Technological University (VTU)  
**Domain:** Healthcare (Rural Maternal & Infant Care)  
**Platform:** Android  
**Tech Stack:** GenAI (Gemini API) + Room DB + WorkManager + MPAndroidChart  

---

## 1. Problem Statement
New mothers in villages often stop "Exclusive Breastfeeding" early due to myths or return-to-work pressure. They lack a structured way to track their baby's weight gain or vaccination milestones once they leave the hospital, resulting in missed immunizations and a lack of proper nutritional tracking during the critical first year.

## 2. Vision - What Is Shishu-Sneh?
Shishu-Sneh is a "Baby’s First Year Guide" designed to act as a digital elder. It provides weekly developmental milestones, a simple weight tracker, and an immunization calendar. By integrating GenAI, the app goes beyond static tips to provide dynamic, personalized feeding guides based on locally available ingredients, ensuring mothers have access to expert, culturally relevant medical guidance.

## 3. Scope of the App

**In Scope:**
*   Digital offline growth chart logging and visualization.
*   Automated vaccination alarms and scheduling.
*   GenAI-powered dynamic local feeding guide.

**Out of Scope:**
*   Multi-hospital or district-level centralized sync.
*   E-commerce features for baby products.
*   iOS version (Android only for now).

## 4. Technical Implementation

| Component | Technology Used | Description |
| :--- | :--- | :--- |
| **Visualization** | MPAndroidChart | For plotting clear, readable growth trend lines based on historical data. |
| **Background Alarms** | WorkManager | To schedule and ensure vaccination reminders fire accurately even if the app is closed. |
| **Database** | Room DB | For secure, private, offline-first storage of the baby's health and milestone logs. |
| **GenAI Integration** | Google AI Studio SDK (Gemini) | To process user-supplied local ingredients and generate tailored pediatric nutritional advice. |
