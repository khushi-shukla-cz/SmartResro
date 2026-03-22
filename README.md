# SmartRestro 🍽️

SmartRestro is an Android food-ordering demo app built using **Kotlin** and **ViewBinding**.  
It includes a restaurant-style user flow from splash screen to menu browsing, order customization, and cart review.

## ✨ Features

- **Splash Screen** with timed navigation to Home
- **Home Screen** with:
  - Auto-flipping promo banners
  - Clickable food gallery with item info dialogs
  - Navigation to Menu, My Orders, About, and Demo Lab
- **Menu Screen** with a RecyclerView grid of food items
- **Order Customization**:
  - Quantity selector
  - Size selection (Small / Medium / Large)
  - Add-ons (Cheese, Sauce, Olives)
  - Delivery city selection
  - Auto-calculated total price
- **My Orders / Cart**:
  - List of added items
  - Remove item action
  - Grand total + item count
  - Empty cart state
- **About Screen** with tappable phone, email, and map intents
- **Demo Lab Screen** (Spinner + RadioGroup + CheckBox styling experiment)
- **System Broadcast Receivers**:
  - Network connectivity status (Snackbar)
  - Battery/power status alerts (Toast)

## 🛠 Tech Stack

- **Language:** Kotlin
- **UI:** XML + ViewBinding
- **Architecture style:** Activity-based with adapters/models/utils
- **Image loading:** [Coil](https://github.com/coil-kt/coil)
- **Min SDK:** 24
- **Target/Compile SDK:** 35
- **Java/Kotlin JVM target:** 11
- **Build System:** Gradle (Kotlin DSL)

## 📁 Project Structure

```text
app/src/main/
├── java/com/example/smartrestro/
│   ├── activities/      # Splash, Home, Menu, Order, MyOrders, About, DemoLab
│   ├── adapters/        # RecyclerView adapters
│   ├── models/          # FoodItem, OrderItem
│   ├── receivers/       # NetworkReceiver, BatteryReceiver
│   └── utils/           # DummyData, CartManager
├── res/
│   ├── layout/          # Screen and item XML layouts
│   ├── values/          # colors, strings, themes, dimens, integers
│   └── drawable/        # Placeholder/icon resources
└── AndroidManifest.xml
```

## 🚀 Getting Started

### Prerequisites

- **Android Studio** (latest stable recommended)
- **JDK 11**
- Android SDK with API 35 platform installed

### Run in Android Studio

1. Open the project folder in Android Studio.
2. Let Gradle sync complete.
3. Select an emulator or physical Android device (API 24+).
4. Click **Run**.

### Build from terminal

From project root:

```bash
./gradlew assembleDebug
```

On Windows CMD:

```bat
gradlew.bat assembleDebug
```

To install on connected device/emulator:

```bat
gradlew.bat installDebug
```

## 📶 Permissions Used

- `android.permission.INTERNET`
- `android.permission.ACCESS_NETWORK_STATE`

These are used for loading remote food images and monitoring connectivity status.

## 🧪 Testing

Default test dependencies are included:

- Unit test: JUnit
- Instrumentation: AndroidX JUnit, Espresso

Run tests with:

```bat
gradlew.bat test
gradlew.bat connectedAndroidTest
```

## 📌 Notes

- Menu data is currently mock data from `DummyData`.
- Cart state is in-memory via `CartManager` and resets when app restarts.
- This project is suitable for college/demo purposes and can be extended with Room/Firebase/API integration.

---

Built with ❤️ using Kotlin + Android.
