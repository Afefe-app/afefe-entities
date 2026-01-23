# Enums and Status Values Documentation

> **Purpose**: This document provides a comprehensive reference for all enum values and status fields across the Afefe Learning Management System database tables. It serves as a single source of truth for both backend and frontend developers.

## Table of Contents

1. [assignments](#1-table-assignments)
2. [courses](#2-table-courses)
3. [organization](#3-table-organization)
4. [lessons](#4-table-lessons)
5. [course_versions](#5-table-course_versions)
6. [calendar_event](#6-table-calendar_event)
7. [compliance_assignments](#7-table-compliance_assignments)
8. [compliance_requirements](#8-table-compliance_requirements)
9. [quizzes](#9-table-quizzes)
10. [certification_expiries](#10-table-certification_expiries)
11. [compliance_status](#11-table-compliance_status)
12. [content_bookmarks](#12-table-content_bookmarks)
13. [certification_requirements](#13-table-certification_requirements)
14. [content_review](#14-table-content_review)
15. [content_versions](#15-table-content_versions)
16. [content_tags](#16-table-content_tags)
17. [content_notes](#17-table-content_notes)
18. [devices](#18-table-devices)
19. [interactive_activities](#19-table-interactive_activities)
20. [deadlines](#20-table-deadlines)
21. [enrollments](#21-table-enrollments)
22. [lesson_progress](#22-table-lesson_progress)
23. [module_progress](#23-table-module_progress)
24. [my_courses_views](#24-table-my_courses_views)
25. [lesson_assets](#25-table-lesson_assets)
26. [org_members](#26-table-org_members)
27. [proctoring_session](#27-table-proctoring_session)
28. [question_banks](#28-table-question_banks)
29. [questions](#29-table-questions)
30. [quiz_attempt](#30-table-quiz_attempt)
31. [policy_acceptances](#31-table-policy_acceptances)
32. [reminders](#32-table-reminders)
33. [skill_gap_analyses](#33-table-skill_gap_analyses)
34. [assignment_submissions](#34-table-assignment_submissions)
35. [skill_relations](#35-table-skill_relations)
36. [user_skill_assessments](#36-table-user_skill_assessments)
37. [app_user](#37-table-app_user)

---

## Legend

- ✅ **Enum Implemented**: Field uses a Java enum class
- ⚠️ **String Field**: Field uses String type (should ideally be enum)
- 📝 **Needs Documentation**: Field values need further clarification

---

## 1. Table: assignments

### Field: `visibility`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.assessment.model.Assignment`
- **Java Field**: `private String visibility;`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `published` | Assignment is visible and accessible to enrolled learners | Default state for active assignments |
| `draft` | Assignment is being created/edited, not visible to learners | Work-in-progress state for instructors |
| `hidden` | Assignment exists but is not visible to learners | Used for archived or temporarily disabled assignments |

#### Business Logic

- **State Transitions**: 
  - `draft` → `published` (when instructor publishes)
  - `published` → `hidden` (when instructor archives)
  - `hidden` → `published` (when instructor reactivates)
- **Validation**: Only instructors can change visibility
- **Frontend Usage**: Filter assignments by visibility in instructor dashboard

#### Frontend Integration Example

```typescript
enum AssignmentVisibility {
  PUBLISHED = 'published',
  DRAFT = 'draft',
  HIDDEN = 'hidden'
}

// Usage in component
const visibleAssignments = assignments.filter(
  a => a.visibility === AssignmentVisibility.PUBLISHED
);
```

---

## 2. Table: courses

### Field: `currency`

- **Database Type**: `VARCHAR(255) NULLABLE`
- **Implementation**: ✅ Enum implemented
- **Java Class**: `com.tensorpoint.toolkit.tpointcore.commons.Currency`
- **Java Field**: `@Enumerated(value = EnumType.STRING) private Currency currency;`

#### Possible Values

| Value | Description | ISO Code | Context |
|-------|-------------|----------|---------|
| `NGN` | Nigerian Naira | NGN | Default currency for Nigerian market |
| `USD` | US Dollar | USD | International currency option |
| `GBP` | British Pound | GBP | UK market currency |
| `EUR` | Euro | EUR | European market currency |

**Note**: The Currency enum is from the TensorPoint Toolkit library. The exact list of supported currencies may include additional ISO 4217 currency codes. Default value in codebase is `NGN`.

#### Business Logic

- **Purpose**: Defines the currency for course pricing
- **Nullable**: Yes - courses can be free without currency
- **Validation**: Must be a valid ISO 4217 currency code
- **Frontend Usage**: Display price with currency symbol, format according to locale

#### Frontend Integration Example

```typescript
// Currency mapping
const currencySymbols = {
  NGN: '₦',
  USD: '$',
  GBP: '£',
  EUR: '€'
};

// Display price
const formattedPrice = `${currencySymbols[course.currency]}${course.price}`;
```

### Field: `language`

- **Database Type**: `VARCHAR(25) NULLABLE`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.contents.models.Course`
- **Java Field**: `private String language;`

#### Possible Values

| Value | Description | ISO 639-1 Code | Context |
|-------|-------------|----------------|---------|
| `en` | English | en | Default language |
| `fr` | French | fr | French-speaking markets |
| `es` | Spanish | es | Spanish-speaking markets |
| `pt` | Portuguese | pt | Portuguese-speaking markets |
| `ar` | Arabic | ar | Arabic-speaking markets |

**Note**: Language values should follow ISO 639-1 language codes. The system may support additional languages based on market requirements.

#### Business Logic

- **Purpose**: Indicates the primary language of course content
- **Nullable**: Yes - language may not be specified
- **Validation**: Should be valid ISO 639-1 code
- **Frontend Usage**: Filter courses by language, display language badge

### Field: `level`

- **Database Type**: `VARCHAR(255) NULLABLE`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.contents.models.Course`
- **Java Field**: `private String level;`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `beginner` | Entry-level course | For learners new to the subject |
| `intermediate` | Mid-level course | For learners with some experience |
| `advanced` | Expert-level course | For experienced learners |
| `all_levels` | Suitable for all levels | Universal difficulty |

**Note**: These are suggested values. Actual implementation may vary.

#### Business Logic

- **Purpose**: Indicates course difficulty/experience level
- **Nullable**: Yes - level may not be specified
- **Frontend Usage**: Filter courses by difficulty, display level badge, recommend courses based on user skill level

### Field: `status`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ✅ Enum implemented
- **Java Class**: `com.ocean.afefe.entities.modules.contents.models.CourseStatus`
- **Java Field**: `@Enumerated(value = EnumType.STRING) private CourseStatus status;`

#### Enum Values

| Enum Constant | Database Value | Description | Context |
|---------------|----------------|-------------|---------|
| `DRAFT` | `Draft` | Course is being created/edited | Work-in-progress state |
| `PUBLISHED` | `Published` | Course is live and available | Active course state |

#### Business Logic

- **State Transitions**: 
  - `DRAFT` → `PUBLISHED` (when instructor publishes)
  - `PUBLISHED` → `DRAFT` (when instructor unpublishes)
- **Validation**: Only published courses are visible to learners
- **Frontend Usage**: Show/hide courses based on status, display status badge

#### Frontend Integration Example

```typescript
enum CourseStatus {
  DRAFT = 'Draft',
  PUBLISHED = 'Published'
}

// Filter published courses
const publishedCourses = courses.filter(
  c => c.status === CourseStatus.PUBLISHED
);
```

---

## 3. Table: organization

### Field: `plan_tier`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ✅ Enum implemented
- **Java Class**: `com.ocean.afefe.entities.modules.auth.models.OrgPlanTier`
- **Java Field**: `@Enumerated(value = EnumType.STRING) private OrgPlanTier planTier;`

#### Enum Values

| Enum Constant | Database Value | Description | Context |
|---------------|----------------|-------------|---------|
| `TIER1` | `Tier1` | Basic subscription tier | Entry-level plan with limited features |
| `TIER2` | `Tier2` | Standard subscription tier | Mid-tier plan with standard features |
| `TIER3` | `Tier3` | Premium subscription tier | Highest tier with all features |

#### Business Logic

- **Purpose**: Defines organization subscription level
- **State Transitions**: Can be upgraded/downgraded by administrators
- **Feature Access**: Different tiers have different feature access levels
- **Frontend Usage**: Display tier badge, show/hide features based on tier

#### Frontend Integration Example

```typescript
enum OrgPlanTier {
  TIER1 = 'Tier1',
  TIER2 = 'Tier2',
  TIER3 = 'Tier3'
}

// Check feature access
const hasAdvancedFeature = org.planTier === OrgPlanTier.TIER3;
```

### Field: `role`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ✅ Enum implemented
- **Java Class**: `com.ocean.afefe.entities.modules.auth.models.OrganizationRole`
- **Java Field**: `@Enumerated(value = EnumType.STRING) private OrganizationRole role;`

#### Enum Values

| Enum Constant | Database Value | Description | Context |
|---------------|----------------|-------------|---------|
| `OWNER` | `Owner` | Organization owner | Full control over organization |
| `PARTNER` | `Partner` | Partner organization | Collaborative relationship |

#### Business Logic

- **Purpose**: Defines the relationship type of the organization
- **Permissions**: OWNER has full administrative rights
- **Frontend Usage**: Display role badge, control access based on role

---

## 4. Table: lessons

### Field: `content_type`

- **Database Type**: `VARCHAR(30) NULLABLE`
- **Implementation**: ✅ Enum implemented
- **Java Class**: `com.ocean.afefe.entities.modules.contents.models.LessonContentType`
- **Java Field**: `@Enumerated(value = EnumType.STRING) private LessonContentType contentType;`

#### Enum Values

| Enum Constant | Database Value | Description | Context |
|---------------|----------------|-------------|---------|
| `VIDEO` | `Dental Hygiene` | Video content | Note: Description seems incorrect, should be "Video" |
| `TEXT` | `BDS` | Text-based content | Note: Description seems incorrect, should be "Text" |
| `PDF` | `Dental Nursing` | PDF document | Note: Description seems incorrect, should be "PDF" |
| `INTERACTIVE` | `Interactive` | Interactive content | Interactive learning activities |
| `SCORM` | `Scorm` | SCORM package | SCORM-compliant learning content |

**Note**: The enum descriptions appear to be placeholder values and should be updated to match the actual content types.

#### Business Logic

- **Purpose**: Defines the format/type of lesson content
- **Nullable**: Yes - content type may not be specified
- **Rendering**: Frontend must render content differently based on type
- **Frontend Usage**: Display appropriate player/viewer for content type

#### Frontend Integration Example

```typescript
enum LessonContentType {
  VIDEO = 'VIDEO',
  TEXT = 'TEXT',
  PDF = 'PDF',
  INTERACTIVE = 'INTERACTIVE',
  SCORM = 'SCORM'
}

// Render based on type
const renderLesson = (lesson: Lesson) => {
  switch (lesson.contentType) {
    case LessonContentType.VIDEO:
      return <VideoPlayer src={lesson.contentUrl} />;
    case LessonContentType.PDF:
      return <PDFViewer src={lesson.contentUrl} />;
    case LessonContentType.INTERACTIVE:
      return <InteractiveActivity data={lesson.activityData} />;
    default:
      return <TextContent content={lesson.content} />;
  }
};
```

---

## 5. Table: course_versions

### Field: `status`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.contents.models.CourseVersion`
- **Java Field**: `private String status;`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `draft` | Version is being created/edited | Work-in-progress state |
| `published` | Version is live and active | Active version visible to learners |
| `archived` | Version is no longer active | Historical version, replaced by newer version |

#### Business Logic

- **Purpose**: Tracks publication status of course versions
- **State Transitions**: 
  - `draft` → `published` (when version is released)
  - `published` → `archived` (when superseded by new version)
- **Version Control**: Only one version can be `published` at a time per course
- **Frontend Usage**: Display version history, show current active version

---

## 6. Table: calendar_event

### Field: `event_type`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ✅ Enum implemented
- **Java Class**: `com.ocean.afefe.entities.modules.calendar.model.CalendarEventType`
- **Java Field**: `@Enumerated(value = EnumType.STRING) private CalendarEventType eventType;`

#### Enum Values

| Enum Constant | Database Value | Description | Context |
|---------------|----------------|-------------|---------|
| `DEFAULT` | `Default` | Standard calendar event | Regular scheduled event |
| `VIDEO_CONFERENCING` | `Video conferencing` | Video conference meeting | Includes video call integration |

#### Business Logic

- **Purpose**: Categorizes calendar events by type
- **Features**: VIDEO_CONFERENCING events may include video call links
- **Frontend Usage**: Display appropriate icon, show video call button for conferencing events

#### Frontend Integration Example

```typescript
enum CalendarEventType {
  DEFAULT = 'Default',
  VIDEO_CONFERENCING = 'Video conferencing'
}

// Display event with appropriate UI
const EventIcon = ({ eventType }) => {
  return eventType === CalendarEventType.VIDEO_CONFERENCING 
    ? <VideoIcon /> 
    : <CalendarIcon />;
};
```

### Field: `status`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ✅ Enum implemented
- **Java Class**: `com.ocean.afefe.entities.modules.calendar.model.CalendarEventStatus`
- **Java Field**: `@Enumerated(value = EnumType.STRING) private CalendarEventStatus status;`

#### Enum Values

| Enum Constant | Database Value | Description | Context |
|---------------|----------------|-------------|---------|
| `PENDING` | `pending` | Event is scheduled for future | Upcoming event |
| `MISSED` | `Missed` | Event time has passed without completion | Past event that wasn't attended |
| `ONGOING` | `Ongoing` | Event is currently happening | Active event |
| `CANCELED` | `Cancelled` | Event was cancelled | Cancelled by instructor |
| `RESCHEDULED` | `Rescheduled` | Event time was changed | Moved to different time |
| `COMPLETED` | `Completed` | Event finished successfully | Past event that was completed |

#### Business Logic

- **State Transitions**: 
  - `PENDING` → `ONGOING` (when event starts)
  - `ONGOING` → `COMPLETED` (when event ends)
  - `PENDING` → `CANCELED` (when cancelled)
  - `PENDING` → `RESCHEDULED` (when time changes)
  - `PENDING` → `MISSED` (when time passes without attendance)
- **Validation**: Only PENDING events can be cancelled or rescheduled
- **Frontend Usage**: Color-code events by status, filter by status, show status badge

#### Frontend Integration Example

```typescript
enum CalendarEventStatus {
  PENDING = 'pending',
  MISSED = 'Missed',
  ONGOING = 'Ongoing',
  CANCELED = 'Cancelled',
  RESCHEDULED = 'Rescheduled',
  COMPLETED = 'Completed'
}

// Status colors
const statusColors = {
  [CalendarEventStatus.PENDING]: '#FFA500',
  [CalendarEventStatus.ONGOING]: '#00FF00',
  [CalendarEventStatus.COMPLETED]: '#008000',
  [CalendarEventStatus.CANCELED]: '#FF0000',
  [CalendarEventStatus.RESCHEDULED]: '#0000FF',
  [CalendarEventStatus.MISSED]: '#808080'
};
```

---

## 7. Table: compliance_assignments

### Field: `status`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.certification.model.ComplianceAssignment`
- **Java Field**: `private String status; // assigned, in_progress, satisfied, overdue, waived`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `assigned` | Compliance requirement assigned to user | Initial state when assigned |
| `in_progress` | User is working on completing requirement | User has started but not completed |
| `satisfied` | Requirement has been completed | User met the compliance requirement |
| `overdue` | Requirement deadline has passed | Past due date without completion |
| `waived` | Requirement was waived/exempted | Admin waived the requirement |

#### Business Logic

- **Purpose**: Tracks completion status of compliance assignments
- **State Transitions**: 
  - `assigned` → `in_progress` (when user starts)
  - `in_progress` → `satisfied` (when completed)
  - `assigned`/`in_progress` → `overdue` (when deadline passes)
  - Any → `waived` (when admin waives)
- **Validation**: Only satisfied assignments count toward compliance
- **Frontend Usage**: Show compliance dashboard, filter by status, display alerts for overdue

---

## 8. Table: compliance_requirements

### Field: `requirement_type`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.certification.model.ComplianceRequirement`
- **Java Field**: `private String requirementType; // course, quiz, certificate, policy`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `course` | Completion of a specific course | Course-based compliance |
| `quiz` | Passing a specific quiz | Quiz-based compliance |
| `certificate` | Obtaining a certificate | Certificate-based compliance |
| `policy` | Accepting a policy document | Policy acceptance requirement |

#### Business Logic

- **Purpose**: Defines the type of compliance requirement
- **Validation**: Each type links to different entities (course_id, quiz_id, etc.)
- **Frontend Usage**: Display different UI based on requirement type, show appropriate completion criteria

---

## 9. Table: quizzes

### Field: `grading_method`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.assessment.model.Quiz`
- **Java Field**: `private String gradingMethod;`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `automatic` | System automatically grades quiz | For multiple choice, true/false questions |
| `manual` | Instructor must grade manually | For essay, open-ended questions |
| `hybrid` | Mix of automatic and manual | Some questions auto-graded, others manual |

#### Business Logic

- **Purpose**: Determines how quiz is graded
- **Validation**: Automatic grading requires all questions to be auto-gradable
- **Frontend Usage**: Show grading status, display "pending review" for manual grading

### Field: `visibility`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.assessment.model.Quiz`
- **Java Field**: `private String visibility;`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `published` | Quiz is visible to learners | Active quiz |
| `draft` | Quiz is being created/edited | Work-in-progress |
| `hidden` | Quiz exists but not visible | Archived or disabled |

#### Business Logic

- **Purpose**: Controls quiz visibility to learners
- **State Transitions**: Similar to assignment visibility
- **Frontend Usage**: Filter quizzes, show/hide based on visibility

---

## 10. Table: certification_expiries

### Field: `notification_status`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.certification.model.CertificationExpiry`
- **Java Field**: `private String notificationStatus; // pending, sent, failed`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `pending` | Notification not yet sent | Waiting to be sent |
| `sent` | Notification successfully sent | Email/push notification delivered |
| `failed` | Notification delivery failed | Retry may be needed |

#### Business Logic

- **Purpose**: Tracks status of expiry notification delivery
- **State Transitions**: 
  - `pending` → `sent` (when notification sent)
  - `pending` → `failed` (when delivery fails)
- **Retry Logic**: Failed notifications may be retried
- **Frontend Usage**: Show notification status in admin dashboard

---

## 11. Table: compliance_status

### Field: `overall_status`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.certification.model.ComplianceStatus`
- **Java Field**: `private String overallStatus; // compliant, partial, non_compliant, unknown`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `compliant` | User meets all compliance requirements | Fully compliant |
| `partial` | User meets some but not all requirements | Partially compliant |
| `non_compliant` | User does not meet requirements | Non-compliant |
| `unknown` | Compliance status cannot be determined | Error or missing data |

#### Business Logic

- **Purpose**: Overall compliance status for a user
- **Calculation**: Based on all compliance assignments
- **Frontend Usage**: Display compliance dashboard, show status badge, generate reports

---

## 12. Table: content_bookmarks

### Field: `object_type`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.contents.models.ContentBookmark`
- **Java Field**: `private String objectType;`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `course` | Bookmarked course | User bookmarked entire course |
| `lesson` | Bookmarked lesson | User bookmarked specific lesson |
| `module` | Bookmarked module | User bookmarked module |

#### Business Logic

- **Purpose**: Type of content that was bookmarked
- **Validation**: Must match a valid content entity type
- **Frontend Usage**: Display bookmarks grouped by type, navigate to bookmarked content

---

## 13. Table: certification_requirements

### Field: `requirement_type`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.certification.model.CertificationRequirement`
- **Java Field**: `private String requirementType; // course, quiz, skill, external`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `course` | Completion of a course | Course-based requirement |
| `quiz` | Passing a quiz | Quiz-based requirement |
| `skill` | Achieving a skill level | Skill-based requirement |
| `external` | External certification | External system requirement |

#### Business Logic

- **Purpose**: Type of certification requirement
- **Validation**: Each type links to different entities
- **Frontend Usage**: Display requirement type, show completion criteria

---

## 14. Table: content_review

### Field: `status`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ✅ Enum implemented
- **Java Class**: `com.ocean.afefe.entities.common.Status`
- **Java Field**: `@Enumerated(EnumType.STRING) private Status status;`

#### Enum Values

| Enum Constant | Database Value | Description | Context |
|---------------|----------------|-------------|---------|
| `PENDING` | `PENDING` | Review is pending approval | Awaiting reviewer |
| `APPROVED` | `APPROVED` | Review approved | Content approved for publication |
| `REJECTED` | `REJECTED` | Review rejected | Content needs revision |

#### Business Logic

- **Purpose**: Tracks content review/approval status
- **State Transitions**: 
  - `PENDING` → `APPROVED` (when approved)
  - `PENDING` → `REJECTED` (when rejected)
- **Workflow**: Content must be approved before publication
- **Frontend Usage**: Show review status, display approval workflow

---

## 15. Table: content_versions

### Field: `object_type`

- **Database Type**: `VARCHAR(50) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.contents.models.ContentVersion`
- **Java Field**: `private String objectType;`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `course` | Version of course | Course versioning |
| `lesson` | Version of lesson | Lesson versioning |
| `module` | Version of module | Module versioning |

#### Business Logic

- **Purpose**: Type of content being versioned
- **Validation**: Must match valid content entity
- **Frontend Usage**: Display version history, compare versions

---

## 16. Table: content_tags

### Field: `content_type`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.taxonomy.models.ContentTag`
- **Java Field**: `private String contentType;`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `course` | Tag applied to course | Course tagging |
| `lesson` | Tag applied to lesson | Lesson tagging |
| `module` | Tag applied to module | Module tagging |

#### Business Logic

- **Purpose**: Type of content that has the tag
- **Validation**: Must match valid content entity
- **Frontend Usage**: Filter content by tags, display tag clouds

---

## 17. Table: content_notes

### Field: `object_type`

- **Database Type**: `VARCHAR(255) NULLABLE`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.contents.models.ContentNote`
- **Java Field**: `private String objectType;`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `lesson` | Note on a lesson | Lesson notes |
| `course` | Note on a course | Course notes |
| `module` | Note on a module | Module notes |

#### Business Logic

- **Purpose**: Type of content the note is attached to
- **Nullable**: Yes - notes may be general
- **Frontend Usage**: Display notes, filter by object type

---

## 18. Table: devices

### Field: `device_type`

- **Database Type**: `VARCHAR(255) NULLABLE`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.auth.models.Device`
- **Java Field**: `private String deviceType;`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `mobile` | Mobile phone device | Smartphone |
| `tablet` | Tablet device | iPad, Android tablet |
| `desktop` | Desktop computer | PC, Mac |
| `laptop` | Laptop computer | Portable computer |

#### Business Logic

- **Purpose**: Type of device used to access the platform
- **Nullable**: Yes - device type may not be detected
- **Frontend Usage**: Analytics, responsive design decisions

### Field: `platform`

- **Database Type**: `VARCHAR(255) NULLABLE`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.auth.models.Device`
- **Java Field**: `private String platform;`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `iOS` | Apple iOS | iPhone, iPad |
| `Android` | Google Android | Android phones/tablets |
| `Web` | Web browser | Desktop/laptop browsers |
| `Windows` | Microsoft Windows | Windows devices |
| `macOS` | Apple macOS | Mac computers |

#### Business Logic

- **Purpose**: Operating system/platform of the device
- **Nullable**: Yes - platform may not be detected
- **Frontend Usage**: Platform-specific features, analytics

---

## 19. Table: interactive_activities

### Field: `activity_type`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ✅ Enum implemented
- **Java Class**: `com.ocean.afefe.entities.modules.contents.models.InteractiveActivityType`
- **Java Field**: `@Enumerated(EnumType.STRING) private InteractiveActivityType activityType;`

#### Enum Values

| Enum Constant | Database Value | Description | Context |
|---------------|----------------|-------------|---------|
| `HOTSPOT` | `Hotspot` | Hotspot interaction | Clickable areas on image |
| `DRAG_DROP` | `Drag drop` | Drag and drop activity | Drag items to targets |
| `LAB` | `Lab` | Lab simulation | Virtual lab environment |
| `SIM` | `Sim` | Simulation activity | Interactive simulation |

#### Business Logic

- **Purpose**: Type of interactive learning activity
- **Rendering**: Each type requires different UI components
- **Frontend Usage**: Render appropriate interactive component

#### Frontend Integration Example

```typescript
enum InteractiveActivityType {
  HOTSPOT = 'Hotspot',
  DRAG_DROP = 'Drag drop',
  LAB = 'Lab',
  SIM = 'Sim'
}

// Render activity
const renderActivity = (activity: InteractiveActivity) => {
  switch (activity.activityType) {
    case InteractiveActivityType.HOTSPOT:
      return <HotspotActivity config={activity.configJson} />;
    case InteractiveActivityType.DRAG_DROP:
      return <DragDropActivity config={activity.configJson} />;
    case InteractiveActivityType.LAB:
      return <LabSimulation config={activity.configJson} />;
    case InteractiveActivityType.SIM:
      return <Simulation config={activity.configJson} />;
  }
};
```

---

## 20. Table: deadlines

### Field: `kind`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.enrollments.models.Deadline`
- **Java Field**: `private String kind; // course, module, lesson, certification`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `course` | Course completion deadline | Deadline for entire course |
| `module` | Module completion deadline | Deadline for module |
| `lesson` | Lesson completion deadline | Deadline for lesson |
| `certification` | Certification deadline | Deadline for certification |

#### Business Logic

- **Purpose**: Type of deadline
- **Validation**: Must match valid entity type
- **Frontend Usage**: Display deadlines, show countdown timers, send reminders

---

## 21. Table: enrollments

### Field: `status`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.enrollments.models.Enrollment`
- **Java Field**: `private String status; // enrolled, in_progress, completed, withdrawn`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `enrolled` | User is enrolled in course | Initial enrollment state |
| `in_progress` | User is actively taking course | User has started course |
| `completed` | User finished the course | Course completed successfully |
| `withdrawn` | User withdrew from course | User dropped out |

#### Business Logic

- **State Transitions**: 
  - `enrolled` → `in_progress` (when user starts)
  - `in_progress` → `completed` (when finished)
  - Any → `withdrawn` (when user withdraws)
- **Progress Tracking**: Status affects progress calculation
- **Frontend Usage**: Show enrollment status, filter enrollments, display progress

#### Frontend Integration Example

```typescript
enum EnrollmentStatus {
  ENROLLED = 'enrolled',
  IN_PROGRESS = 'in_progress',
  COMPLETED = 'completed',
  WITHDRAWN = 'withdrawn'
}

// Status colors
const statusColors = {
  [EnrollmentStatus.ENROLLED]: '#808080',
  [EnrollmentStatus.IN_PROGRESS]: '#FFA500',
  [EnrollmentStatus.COMPLETED]: '#008000',
  [EnrollmentStatus.WITHDRAWN]: '#FF0000'
};
```

---

## 22. Table: lesson_progress

### Field: `status`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.enrollments.models.LessonProgress`
- **Java Field**: `private String status; // not_started, in_progress, completed`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `not_started` | Lesson not yet started | Initial state |
| `in_progress` | Lesson is being viewed | User has started lesson |
| `completed` | Lesson finished | User completed lesson |

#### Business Logic

- **State Transitions**: 
  - `not_started` → `in_progress` (when user opens lesson)
  - `in_progress` → `completed` (when lesson finished)
- **Progress Calculation**: Used to calculate course progress
- **Frontend Usage**: Show lesson status, display progress indicators

---

## 23. Table: module_progress

### Field: `status`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.enrollments.models.ModuleProgress`
- **Java Field**: `private String status;`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `not_started` | Module not yet started | Initial state |
| `in_progress` | Module is being worked on | User has started module |
| `completed` | Module finished | All lessons in module completed |

#### Business Logic

- **Purpose**: Tracks module completion status
- **Calculation**: Based on lesson progress within module
- **Frontend Usage**: Show module progress, display completion status

---

## 24. Table: my_courses_views

### Field: `status`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.enrollments.models.MyCourseView`
- **Java Field**: `private String status; // enrolled, completed, withdrawn, etc.`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `enrolled` | User is enrolled | Active enrollment |
| `completed` | Course completed | Finished course |
| `withdrawn` | User withdrew | Dropped course |

#### Business Logic

- **Purpose**: Status for user's course view
- **Frontend Usage**: Filter "My Courses" by status, display status badges

---

## 25. Table: lesson_assets

### Field: `asset_type`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ✅ Enum implemented
- **Java Class**: `com.ocean.afefe.entities.modules.contents.models.LessonAssetType`
- **Java Field**: `@Enumerated(value = EnumType.STRING) private LessonAssetType assetType;`

#### Enum Values

| Enum Constant | Database Value | Description | Context |
|---------------|----------------|-------------|---------|
| `VIDEO_URL` | `Video URL` | Video file URL | External video link |
| `PDF_URL` | `PDF URL` | PDF document URL | PDF file link |
| `HTML` | `HTML` | HTML content | HTML content asset |
| `SCORM_PKG` | `Scorm PKG` | SCORM package | SCORM content package |

#### Business Logic

- **Purpose**: Type of lesson asset file
- **Rendering**: Each type requires different handling
- **Frontend Usage**: Display appropriate viewer/downloader

---

## 26. Table: org_members

### Field: `invitation_status`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ✅ Enum implemented
- **Java Class**: `com.ocean.afefe.entities.common.Status`
- **Java Field**: `@Enumerated(value = EnumType.STRING) private Status invitationStatus;`

#### Enum Values

| Enum Constant | Database Value | Description | Context |
|---------------|----------------|-------------|---------|
| `PENDING` | `PENDING` | Invitation pending | Invitation sent, awaiting response |
| `APPROVED` | `APPROVED` | Invitation accepted | User accepted invitation |
| `REJECTED` | `REJECTED` | Invitation rejected | User declined invitation |

#### Business Logic

- **State Transitions**: 
  - `PENDING` → `APPROVED` (when accepted)
  - `PENDING` → `REJECTED` (when declined)
- **Permissions**: Only APPROVED members have access
- **Frontend Usage**: Show invitation status, display pending invitations

---

## 27. Table: proctoring_session

### Field: `status`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.assessment.model.ProctoringSession`
- **Java Field**: `private String status; // active, ended, flagged, etc.`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `active` | Proctoring session is active | Session in progress |
| `ended` | Session ended normally | Completed successfully |
| `flagged` | Session flagged for review | Suspicious activity detected |
| `terminated` | Session terminated early | Ended prematurely |

#### Business Logic

- **Purpose**: Tracks proctoring session state
- **State Transitions**: 
  - `active` → `ended` (normal completion)
  - `active` → `flagged` (suspicious activity)
  - `active` → `terminated` (early termination)
- **Frontend Usage**: Show session status, display flags for review

---

## 28. Table: question_banks

### Field: `visibility`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.assessment.model.QuestionBank`
- **Java Field**: `private String visibility; // private, public, etc.`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `private` | Bank is private to organization | Only org members can access |
| `public` | Bank is publicly accessible | All users can access |
| `shared` | Bank is shared with specific orgs | Limited sharing |

#### Business Logic

- **Purpose**: Controls question bank access
- **Frontend Usage**: Filter question banks, show visibility badge

---

## 29. Table: questions

### Field: `question_type`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ✅ Enum implemented
- **Java Class**: `com.ocean.afefe.entities.modules.assessment.model.QuestionType`
- **Java Field**: `@Enumerated(value = EnumType.STRING) private QuestionType questionType;`

#### Enum Values

| Enum Constant | Database Value | Description | Context |
|---------------|----------------|-------------|---------|
| `MULTI_CHOICE` | `Multi-choice` | Multiple choice question | Select one or more options |
| `TRUE_FALSE` | `True/False` | True/false question | Binary choice question |

#### Business Logic

- **Purpose**: Type of quiz question
- **Grading**: MULTI_CHOICE can be auto-graded, TRUE_FALSE is always auto-graded
- **Frontend Usage**: Render appropriate question UI, handle answers differently

#### Frontend Integration Example

```typescript
enum QuestionType {
  MULTI_CHOICE = 'Multi-choice',
  TRUE_FALSE = 'True/False'
}

// Render question
const renderQuestion = (question: Question) => {
  if (question.questionType === QuestionType.TRUE_FALSE) {
    return <TrueFalseQuestion question={question} />;
  }
  return <MultipleChoiceQuestion question={question} />;
};
```

---

## 30. Table: quiz_attempt

### Field: `status`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.assessment.model.QuizAttempt`
- **Java Field**: `private String status; // in_progress, submitted, graded, etc.`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `in_progress` | Quiz attempt in progress | User is taking quiz |
| `submitted` | Quiz submitted, awaiting grading | Submitted but not graded |
| `graded` | Quiz has been graded | Grading complete |
| `abandoned` | Quiz attempt abandoned | User left without submitting |

#### Business Logic

- **State Transitions**: 
  - `in_progress` → `submitted` (when user submits)
  - `submitted` → `graded` (when graded)
  - `in_progress` → `abandoned` (when user leaves)
- **Frontend Usage**: Show attempt status, display results when graded

---

## 31. Table: policy_acceptances

### Field: `policy_key`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.certification.model.PolicyAcceptance`
- **Java Field**: `private String policyKey;`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `terms_of_service` | Terms of Service | TOS acceptance |
| `privacy_policy` | Privacy Policy | Privacy policy acceptance |
| `code_of_conduct` | Code of Conduct | Conduct policy |
| `data_processing` | Data Processing Agreement | GDPR/data processing |

**Note**: Policy keys are configurable and may vary by organization.

#### Business Logic

- **Purpose**: Type of policy being accepted
- **Validation**: Must match valid policy document
- **Frontend Usage**: Show policy acceptance status, require acceptance before access

---

## 32. Table: reminders

### Field: `channel`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.enrollments.models.Reminder`
- **Java Field**: `private String channel; // in_app, email, push`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `in_app` | In-app notification | Platform notification |
| `email` | Email notification | Email reminder |
| `push` | Push notification | Mobile push notification |
| `sms` | SMS notification | Text message (if supported) |

#### Business Logic

- **Purpose**: Delivery channel for reminder
- **Frontend Usage**: User preference settings, notification center

---

## 33. Table: skill_gap_analyses

### Field: `source`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.analytics.model.SkillGapAnalysis`
- **Java Field**: `private String source;`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `job_role` | Based on job role requirements | Job role gap analysis |
| `career_path` | Based on career path | Career progression analysis |
| `manual` | Manually created | Admin-created analysis |
| `ai` | AI-generated analysis | Automated analysis |

#### Business Logic

- **Purpose**: Source of the gap analysis
- **Frontend Usage**: Display analysis source, filter analyses

---

## 34. Table: assignment_submissions

### Field: `status`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.assessment.model.AssignmentSubmission`
- **Java Field**: `private String status; // submitted, graded, pending, etc.`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `submitted` | Assignment submitted | User submitted work |
| `graded` | Assignment has been graded | Instructor graded submission |
| `pending` | Awaiting grading | Submitted but not yet graded |
| `returned` | Returned for revision | Needs resubmission |

#### Business Logic

- **State Transitions**: 
  - `submitted` → `pending` (when submitted)
  - `pending` → `graded` (when graded)
  - `graded` → `returned` (if needs revision)
- **Frontend Usage**: Show submission status, display grades, allow resubmission

---

## 35. Table: skill_relations

### Field: `relation_type`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.analytics.model.SkillRelation`
- **Java Field**: `private String relationType; // prerequisite, broader, narrower`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `prerequisite` | Prerequisite skill | Required before learning this skill |
| `broader` | Broader skill category | Parent skill category |
| `narrower` | Narrower skill subcategory | Child skill subcategory |
| `related` | Related skill | Similar or complementary skill |

#### Business Logic

- **Purpose**: Type of relationship between skills
- **Frontend Usage**: Display skill hierarchy, show prerequisites, skill maps

---

## 36. Table: user_skill_assessments

### Field: `source`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.analytics.model.UserSkillAssessment`
- **Java Field**: `private String source; // self, quiz, assignment, ai, instructor`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `self` | Self-assessment | User self-evaluated |
| `quiz` | Quiz-based assessment | Assessed via quiz performance |
| `assignment` | Assignment-based | Assessed via assignment |
| `ai` | AI-generated assessment | Automated AI assessment |
| `instructor` | Instructor assessment | Manual instructor evaluation |

#### Business Logic

- **Purpose**: Source of skill assessment
- **Reliability**: Different sources have different confidence levels
- **Frontend Usage**: Display assessment source, show confidence indicators

---

## 37. Table: app_user

### Field: `channel`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ⚠️ String field
- **Java Class**: `com.ocean.afefe.entities.modules.appuser.entities.AppUser`
- **Java Field**: `private String channel;`

#### Possible Values

| Value | Description | Context |
|-------|-------------|---------|
| `web` | Web application | Browser-based access |
| `mobile` | Mobile application | Native mobile app |
| `api` | API access | Programmatic access |

**Note**: Channel values are specific to the application user system and may vary.

#### Business Logic

- **Purpose**: Identifies the access channel for app users
- **Frontend Usage**: Channel-specific features, analytics

### Field: `status`

- **Database Type**: `VARCHAR(255) NOT NULL`
- **Implementation**: ✅ Enum implemented
- **Java Class**: `com.ocean.afefe.entities.modules.appuser.enums.AppUserStatus`
- **Java Field**: `@Enumerated(value = EnumType.STRING) private AppUserStatus status;`

#### Enum Values

| Enum Constant | Database Value | Description | Context |
|---------------|----------------|-------------|---------|
| `ACTIVE` | `ACTIVE` (id: 1) | User account is active | Active app user |
| `DISABLED` | `DISABLED` (id: 2) | User account is disabled | Disabled/inactive account |

#### Business Logic

- **Purpose**: Application user account status
- **State Transitions**: 
  - `ACTIVE` → `DISABLED` (when admin disables)
  - `DISABLED` → `ACTIVE` (when admin reactivates)
- **Frontend Usage**: Show account status, control access

---

## Summary Statistics

- **Total Tables Documented**: 37
- **Enum Implemented Fields**: 15
- **String Fields (Should be Enum)**: 35
- **Total Fields Documented**: 50

## Recommendations

1. **Convert String Fields to Enums**: Many status fields are currently strings but should be enums for type safety and validation
2. **Standardize Naming**: Some enums use different naming conventions (e.g., `DRAFT` vs `draft`)
3. **Add Missing Descriptions**: Some enum descriptions appear to be placeholders (e.g., LessonContentType)
4. **Document State Transitions**: Add state machine diagrams for complex workflows
5. **Frontend Constants**: Generate TypeScript/JavaScript constants from these enums for frontend use

## Frontend Integration Guide

### TypeScript Enum Generation

For frontend teams, consider generating TypeScript enums from the Java enums:

```typescript
// Example: Course Status
export enum CourseStatus {
  DRAFT = 'Draft',
  PUBLISHED = 'Published'
}

// Example: Calendar Event Status
export enum CalendarEventStatus {
  PENDING = 'pending',
  MISSED = 'Missed',
  ONGOING = 'Ongoing',
  CANCELED = 'Cancelled',
  RESCHEDULED = 'Rescheduled',
  COMPLETED = 'Completed'
}
```

### API Response Handling

When receiving enum values from the API:

```typescript
// Safe enum parsing
function parseCourseStatus(status: string): CourseStatus | null {
  const statusMap: Record<string, CourseStatus> = {
    'Draft': CourseStatus.DRAFT,
    'Published': CourseStatus.PUBLISHED
  };
  return statusMap[status] || null;
}
```

### Display Helpers

Create display helpers for enum values:

```typescript
// Status badge colors
const statusColors: Record<string, string> = {
  [CourseStatus.DRAFT]: '#808080',
  [CourseStatus.PUBLISHED]: '#008000'
};

// Status labels
const statusLabels: Record<string, string> = {
  [CourseStatus.DRAFT]: 'Draft',
  [CourseStatus.PUBLISHED]: 'Published'
};
```

---

## Last Updated

**Date**: 2026-01-06  
**Version**: 1.0  
**Maintained By**: Afefe Development Team

---

## Questions or Updates?

If you find any discrepancies or need to add new enum values, please:
1. Update the Java enum class
2. Update this documentation
3. Notify the frontend team
4. Update API documentation




