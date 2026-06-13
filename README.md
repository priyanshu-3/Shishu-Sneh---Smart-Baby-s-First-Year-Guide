# 🍼 Shishu-Sneh — Smart Baby's First Year Guide

<p align="center">
  <strong>A digital companion for new mothers — especially in rural India — providing vaccination tracking, growth monitoring, and AI-powered nutritional guidance.</strong>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-brightgreen?style=for-the-badge&logo=android&logoColor=white" alt="Platform">
  <img src="https://img.shields.io/badge/Backend-Spring%20Boot%203.4-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Backend">
  <img src="https://img.shields.io/badge/Database-Supabase%20PostgreSQL-3ECF8E?style=for-the-badge&logo=supabase&logoColor=white" alt="Database">
  <img src="https://img.shields.io/badge/AI-Google%20Gemini-4285F4?style=for-the-badge&logo=google&logoColor=white" alt="AI">
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
</p>

---

## 📋 Project Details

| Key Info | Details |
| :--- | :--- |
| **Developer** | Priyanshu Mehra |
| **USN** | 1VE22CS119 |
| **University** | Visvesvaraya Technological University (VTU) |
| **Domain** | Healthcare (Rural Maternal & Infant Care) |

---

## 1. Problem Statement

New mothers in rural villages often cease **Exclusive Breastfeeding** ahead of recommended periods due to deep-seated cultural myths or immediate return-to-work pressures. Furthermore, once discharged from local hospitals, they lack a structured mechanism to monitor critical infant milestones, leading to:
* ❌ **Missed Immunizations:** Lack of clear tracking against the Indian National Immunization Schedule.
* ❌ **Nutritional Gaps:** Absence of structured guidance on weaning and infant nutrition during the critical first 12 months.
* ❌ **Accessibility Barriers:** Lack of immediate, culturally relevant, and accessible expert medical guidance.

---

## 2. Vision — What Is Shishu-Sneh?

**Shishu-Sneh** (शिशु-स्नेह, meaning *"Baby's Affection"*) is an offline-first, AI-augmented infant health assistant designed to act as a **digital elder** for new mothers. 

### Key Capabilities:
* 📅 **Milestone Indexing:** Provides highly structured, weekly developmental milestones.
* ⚖️ **Growth Auditing:** Simple weight, height, and health parameter logging with local data visualization.
* 💉 **Immunization Matrix:** Automates vaccination calendars based on the Indian NIS with robust local reminder queues.
* 🍲 **GenAI-Powered Nutrition:** Leveraging Google's Gemini AI to compute personalized feeding guides using locally available, low-cost ingredients instead of static, generalized PDFs.

---

## 3. Project Scope

| In Scope ✅ | Out of Scope ❌ |
| :--- | :--- |
| Offline-first growth logging & trend visualization | Multi-hospital/District-level cloud sync infrastructure |
| Automated background vaccination alarms via Android | Built-in e-commerce portals or commercial product placement |
| GenAI-driven customized local ingredient recipe engines | Multi-platform deployment (Native Android Exclusive for MVP) |
| Cloud backup orchestration via Supabase PostgreSQL | Multi-lingual voice processing systems (Planned) |

---

## 4. System Architecture

```text
┌────────────────────────────────────────────────────────┐
│                     Android Client                     │
│       (Room DB · WorkManager · MPAndroidChart)         │
└──────────────────────────┬─────────────────────────────┘
                           │ 
                           │ REST API
                           ▼
┌────────────────────────────────────────────────────────┐
│                  Spring Boot Backend                   │
│  ┌──────────────────┐ ┌──────────────┐ ┌────────────┐  │
│  │  Baby Controller │ │ Vaccine Serv │ │ Gemini AI  │  │
│  └────────┬─────────┘ └──────┬───────┘ └─────┬──────┘  │
│           │                  │               │         │
│  ┌────────▼──────────────────▼───────┐ ┌─────▼──────┐  │
│  │          Spring Data JPA          │ │  WebClient │  │
│  └────────────────┬──────────────────┘ └─────┬──────┘  │
└───────────────────┼──────────────────────────┼─────────┘
                    │                          │
        ┌───────────▼──────────┐    ┌──────────▼─────────┐
        │  Supabase PostgreSQL │    │  Google Gemini API │
        │   (Cloud Database)   │    │   (AI Generation)  │
        └──────────────────────┘    └────────────────────┘
```

---

## 5. Tech Stack & Tooling

| Layer | Technology | Purpose |
| :--- | :--- | :--- |
| **Backend Engineering** | Spring Boot 3.4.5, Java 17, Spring Data JPA | REST API server & business logic |
| **Database (Dev)** | H2 (Embedded In-Memory) | Local development & testing sandbox |
| **Database (Prod)** | Supabase PostgreSQL 17 | Cloud-hosted production instance |
| **Artificial Intelligence** | Google Gemini API | AI-powered nutrition guide generation |
| **Mobile Architecture** | Android Native | Client-side mobile application |
| **Local Storage** | Room DB (SQLite) | Offline-first data persistence |
| **Background Scheduling** | WorkManager | Vaccination reminder notifications |
| **Data Visualization** | MPAndroidChart | Growth trend line charts |
| **Design & UI/UX** | Google Stitch | Premium UI mockups & design tokens |

---

## 6. REST API Endpoints

### Endpoint Summary

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/baby/register` | Provisions a new infant profile schema. |
| `GET` | `/api/baby/{id}` | Fetches infant metadata by Profile ID. |
| `GET` | `/api/baby/all` | Retrieves a catalog of all registered profiles. |
| `GET` | `/api/baby/{id}/vaccines` | Computes Indian NIS immunization timelines for the target profile. |
| `POST` | `/api/baby/{id}/health-log` | Commits an incremental growth/weight log entry. |
| `GET` | `/api/baby/{id}/health-logs` | Returns historical health logs formatted for MPAndroidChart parsing. |
| `POST` | `/api/baby/nutrition` | Interrogates Gemini AI for tailored weaning recipes. |
| `GET` | `/api/baby/health` | Service health check. |

### Core Payload Handshakes

**1. Register an Infant**

Request: `POST /api/baby/register`

```bash
curl -X POST http://localhost:8080/api/baby/register \
  -H "Content-Type: application/json" \
  -d '{"name": "Arya", "dateOfBirth": "2025-03-15", "birthWeight": 3.2}'
```

**2. Query Vaccine Array**

Request: `GET /api/baby/1/vaccines`

```bash
curl http://localhost:8080/api/baby/1/vaccines
```

**3. AI Weaning Advisor Engine**

Request: `POST /api/baby/nutrition`

```bash
curl -X POST http://localhost:8080/api/baby/nutrition \
  -H "Content-Type: application/json" \
  -d '{"ageInMonths": 8, "ingredients": ["rice", "dal", "banana"]}'
```

---

## 7. UI/UX Paradigm

The user interface layouts are mocked inside Google Stitch using the customized **Nurturing Touch** aesthetic framework to optimize usability for rural deployments:

| Screen | Design Details |
| :--- | :--- |
| **Registration Interface** | High-contrast text layout with floating input modules. |
| **Home Dashboard** | Dynamic contextual greetings, graphical ring progress trackers for vaccine schedules, and countdown states. |
| **Timeline Metrics** | Discrete color-coded statuses for historical and upcoming immunization actions. |
| **Data Layouts** | Implements Plus Jakarta Sans typography with a minimum touch-target size of 48px to assist users with varied digital literacy. |

**Color Palette:** Soft Minimalism configured around **Warm Coral** (#E87461) and **Cream** (#FFF9F5).

> 📁 **Note:** Complete interactive HTML vector exports can be audited directly under the `designs/` directory.

---

## 8. Deployment & Execution Strategy

### Hardware/Runtime Prerequisites
- Java Development Kit (JDK) 17 or higher
- Maven 3.8+ (or localized Maven Wrapper distribution)

### Local Development Target (H2 Database Stack)

```bash
cd backend
./mvnw spring-boot:run
```

- **API Base Vector:** `http://localhost:8080`
- **H2 Management Console:** `http://localhost:8080/h2-console`

### Production Cloud Environment Setup (Supabase + Gemini Engines)

```bash
cd backend
export SUPABASE_DB_PASSWORD=your-database-password
export GEMINI_API_KEY=your-gemini-api-key
./mvnw spring-boot:run -Dspring-boot.run.profiles=supabase
```

---

## 9. Codebase Blueprint

```
Shishu-Sneh/
├── README.md
├── backend/                              # Spring Boot Backend Project
│   ├── pom.xml                           # Maven Configuration Manifest
│   ├── mvnw                              # Maven Wrapper Executor
│   └── src/main/java/com/shishusneh/
│       ├── ShishuSnehApplication.java    # Core Application Driver
│       ├── controller/                   # Endpoint Interceptors
│       │   └── BabyController.java 
│       ├── dto/                          # Serialization Contracts
│       │   ├── NutritionRequest.java
│       │   └── NutritionResponse.java
│       ├── model/                        # Data Domain Architecture
│       │   ├── BabyProfile.java          
│       │   └── HealthLog.java            
│       ├── repository/                   # Persistence Layer Handlers
│       │   ├── BabyProfileRepository.java
│       │   └── HealthLogRepository.java
│       └── service/                      # Core Business Logic Components
│           ├── BabyProfileService.java
│           ├── GeminiNutritionService.java
│           └── VaccinationService.java   # Indian NIS Business Rules
└── designs/                              # UI Screen Blueprints
    ├── 01-registration.html
    ├── 02-dashboard.html
    └── 05-feeding-guide.html
```

---

<p align="center">
  Made by <strong>Priyanshu Mehra</strong> · VTU · 1VE22CS119
</p>
