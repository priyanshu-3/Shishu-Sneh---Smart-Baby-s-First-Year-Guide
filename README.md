# 🍼 Shishu-Sneh — Smart Baby's First Year Guide

<p align="center">
  <strong>A digital companion for new mothers — especially in rural India — providing vaccination tracking, growth monitoring, and AI-powered nutritional guidance.</strong>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-brightgreen?style=flat-square&logo=android" alt="Platform">
  <img src="https://img.shields.io/badge/Backend-Spring%20Boot%203.4-6DB33F?style=flat-square&logo=spring" alt="Backend">
  <img src="https://img.shields.io/badge/Database-Supabase%20PostgreSQL-3ECF8E?style=flat-square&logo=supabase" alt="Database">
  <img src="https://img.shields.io/badge/AI-Google%20Gemini-4285F4?style=flat-square&logo=google" alt="AI">
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=flat-square&logo=openjdk" alt="Java">
</p>

---

## 📋 Project Details

| | |
|:---|:---|
| **Prepared by** | Priyanshu Mehra |
| **USN** | 1VE22CS119 |
| **University** | Visvesvaraya Technological University (VTU) |
| **Domain** | Healthcare (Rural Maternal & Infant Care) |

---

## 1. Problem Statement

New mothers in villages often stop **Exclusive Breastfeeding** early due to myths or return-to-work pressure. They lack a structured way to track their baby's weight gain or vaccination milestones once they leave the hospital, resulting in:
- ❌ Missed immunizations
- ❌ Lack of proper nutritional tracking during the critical first year
- ❌ No access to expert, culturally relevant medical guidance

## 2. Vision — What Is Shishu-Sneh?

**Shishu-Sneh** (शिशु-स्नेह, meaning "Baby's Affection") is a **Baby's First Year Guide** designed to act as a **digital elder**. It provides:

- 📅 **Weekly developmental milestones**
- ⚖️ **Simple weight & growth tracker**
- 💉 **Immunization calendar** with automated reminders
- 🍲 **GenAI-powered feeding guides** based on locally available ingredients

By integrating Google's Gemini AI, the app goes beyond static tips to provide **dynamic, personalized feeding guides** using local ingredients — ensuring mothers have access to expert, culturally relevant nutritional advice.

## 3. Scope

| In Scope ✅ | Out of Scope ❌ |
|:---|:---|
| Offline growth chart logging & visualization | Multi-hospital/district-level sync |
| Automated vaccination alarms & scheduling | E-commerce for baby products |
| GenAI-powered dynamic local feeding guide | iOS version (Android only for now) |
| Cloud backup via Supabase PostgreSQL | |

---

## 4. Architecture

```
┌─────────────────────────────────────────────────┐
│                 Android Client                   │
│  (Room DB · WorkManager · MPAndroidChart)        │
└──────────────────┬──────────────────────────────┘
                   │ REST API
┌──────────────────▼──────────────────────────────┐
│              Spring Boot Backend                 │
│  ┌────────────┐ ┌──────────────┐ ┌────────────┐ │
│  │ Baby       │ │ Vaccination  │ │ Gemini AI  │ │
│  │ Controller │ │ Service      │ │ Nutrition  │ │
│  └─────┬──────┘ └──────┬───────┘ └─────┬──────┘ │
│        │               │               │        │
│  ┌─────▼───────────────▼───────┐ ┌─────▼──────┐ │
│  │   Spring Data JPA           │ │ WebClient  │ │
│  └─────────────┬───────────────┘ └─────┬──────┘ │
└────────────────┼───────────────────────┼────────┘
                 │                       │
     ┌───────────▼──────────┐  ┌─────────▼─────────┐
     │  Supabase PostgreSQL │  │  Google Gemini API │
     │  (Cloud Database)    │  │  (AI Generation)   │
     └──────────────────────┘  └────────────────────┘
```

## 5. Tech Stack

| Layer | Technology | Purpose |
|:---|:---|:---|
| **Backend** | Spring Boot 3.4.5 (Java 17) | REST API server |
| **Database (Dev)** | H2 (Embedded) | Local development & testing |
| **Database (Prod)** | Supabase PostgreSQL 17 | Cloud-hosted production database |
| **AI Integration** | Google Gemini API | Personalized nutrition guide generation |
| **Android** | Room DB | Offline-first local storage |
| **Scheduling** | WorkManager | Vaccination reminder notifications |
| **Visualization** | MPAndroidChart | Growth trend line charts |
| **UI Design** | Stitch (Google) | Premium UI screen prototyping |

## 6. API Endpoints

| Method | Endpoint | Description |
|:---|:---|:---|
| `POST` | `/api/baby/register` | Register a new baby profile |
| `GET` | `/api/baby/{id}` | Get a baby profile by ID |
| `GET` | `/api/baby/all` | List all registered babies |
| `GET` | `/api/baby/{id}/vaccines` | Get vaccination schedule (21 vaccines, Indian NIS) |
| `POST` | `/api/baby/{id}/health-log` | Add a health/growth log entry |
| `GET` | `/api/baby/{id}/health-logs` | Get all health logs (for charts) |
| `POST` | `/api/baby/nutrition` | AI-powered feeding guide (Gemini) |
| `GET` | `/api/baby/health` | Server health check |

### Example Requests

**Register a Baby:**
```bash
curl -X POST http://localhost:8080/api/baby/register \
  -H "Content-Type: application/json" \
  -d '{"name": "Arya", "dateOfBirth": "2025-03-15", "birthWeight": 3.2}'
```

**Get Vaccination Schedule:**
```bash
curl http://localhost:8080/api/baby/1/vaccines
```

**Get AI Nutrition Guide:**
```bash
curl -X POST http://localhost:8080/api/baby/nutrition \
  -H "Content-Type: application/json" \
  -d '{"ageInMonths": 8, "ingredients": ["rice", "dal", "banana"]}'
```

## 7. UI Design

Premium UI screens designed using Google Stitch with the **"Nurturing Touch"** design system:

| Screen | Description |
|:---|:---|
| **Registration** | Gradient hero, floating card form, elegant inputs |
| **Home Dashboard** | Personalized greeting, stats card with progress ring, vaccination countdown |
| **Vaccination Calendar** | Timeline view, color-coded status cards, filter chips |
| **Growth Tracker** | Professional chart with WHO band, milestone timeline, FAB |
| **AI Feeding Guide** | Ingredient chips, AI recipe card, safety callouts |

**Design Principles:**
- Warm Coral (`#E87461`) + Cream (`#FFF9F5`) palette
- Plus Jakarta Sans typography
- 48px minimum touch targets for rural mothers
- Soft Minimalism aesthetic

> 📎 HTML exports of all screens are available in the [`designs/`](./designs/) directory.

## 8. How to Run

### Prerequisites
- Java 17+ (or any modern JDK)
- Git

### Local Development (H2 Database)
```bash
cd backend
./mvnw spring-boot:run
```
Server starts at `http://localhost:8080`. H2 console available at `http://localhost:8080/h2-console`.

### Production (Supabase PostgreSQL)
```bash
cd backend
export SUPABASE_DB_PASSWORD=your-database-password
export GEMINI_API_KEY=your-gemini-api-key
./mvnw spring-boot:run -Dspring-boot.run.profiles=supabase
```

## 9. Project Structure

```
Shishu-Sneh/
├── README.md
├── backend/                              # Spring Boot Backend
│   ├── pom.xml                           # Maven dependencies
│   ├── mvnw                              # Maven wrapper
│   └── src/main/java/com/shishusneh/
│       ├── ShishuSnehApplication.java    # Entry point
│       ├── controller/
│       │   └── BabyController.java       # REST endpoints
│       ├── dto/
│       │   ├── NutritionRequest.java
│       │   ├── NutritionResponse.java
│       │   └── VaccinationScheduleItem.java
│       ├── model/
│       │   ├── BabyProfile.java          # JPA entity
│       │   └── HealthLog.java            # JPA entity
│       ├── repository/
│       │   ├── BabyProfileRepository.java
│       │   └── HealthLogRepository.java
│       └── service/
│           ├── BabyProfileService.java
│           ├── GeminiNutritionService.java  # Gemini AI
│           └── VaccinationService.java      # Indian NIS
└── designs/                              # UI Screen Exports
    ├── 01-registration.html
    ├── 02-dashboard.html
    ├── 03-vaccination.html
    ├── 04-growth-tracker.html
    └── 05-feeding-guide.html
```

---

<p align="center">
  Made by <strong>Priyanshu Mehra</strong> · VTU · 1VE22CS119
</p>
