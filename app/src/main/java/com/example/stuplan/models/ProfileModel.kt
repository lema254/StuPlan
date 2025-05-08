package com.example.stuplan.models

data class UserViewModel(
    val userId: String = "",
    val displayName: String = "",
    val email: String = "",
    val photoUrl: String? = null,
    val bio: String = "",
    val completedSections: Int = 0,
    val totalSections: Int = 0,
    val joinDate: Long = System.currentTimeMillis(),
    val interests: List<String> = emptyList(),
    val academicLevel: String = "",
    val preferredSubjects: List<String> = emptyList(),
    val notificationSettings: NotificationSettings = NotificationSettings(),
    val themePreference: String = "system" // system, light, dark
)

data class NotificationSettings(
    val enablePushNotifications: Boolean = true,
    val enableEmailNotifications: Boolean = true,
    val studyReminders: Boolean = true,
    val newContentAlerts: Boolean = true
)