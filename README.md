# UI-API Integration Assessment

![Java](https://img.shields.io/badge/Java-17-blue)
![TestNG](https://img.shields.io/badge/TestNG-7.4.0-red)
![REST-Assured](https://img.shields.io/badge/REST_Assured-4.4.0-green)
![Appium](https://img.shields.io/badge/Appium-7.6.0-orange)
![Maven](https://img.shields.io/badge/Maven-3.8.1-blueviolet)

End-to-end test automation framework validating integration between API and mobile UI, created as part of an interview assessment.

## Key Features

✅ API user creation with REST Assured  
✅ Mobile UI validation with Appium  
✅ Data comparison between API and UI  
✅ Automated test data management  
✅ Detailed ExtentReports with test steps  
✅ Maven project structure  

## Test Scenario

1. Creates a user via API  
2. Launches mobile app and logs in with same credentials  
3. Validates profile data shown in app matches API response  

## Prerequisites

- Java JDK 8+
- Maven 3.8.1+
- Appium server
- Android SDK (for mobile tests)
- Mobile emulator/device configured

## Installation

```bash
git clone https://github.com/immy1989/ui-api-integration-assessment.git
cd ui-api-integration-assessment
mvn clean install
