# Test Interview Java Automation

Проект автоматизации тестирования логина на сайте [SauceDemo](https://www.saucedemo.com/) с использованием Java, Selenium WebDriver, JUnit 5 и Allure Reports.


## Предварительные требования

1. **Java Development Kit (JDK)** версии 11 или выше
2. **Apache Maven** версии 3.6 или выше
3. **Браузер**

## Установка и настройка

1. Клонируйте репозиторий или скачайте проект
2. Убедитесь, что установлены все предварительные требования
3. Зависимости будут автоматически загружены при первой сборке проекта

## Запуск тестов

### Запуск всех тестов

По умолчанию тесты запускаются в Chrome:

```bash
mvn clean test
```

### Выбор браузера

Вы можете запускать тесты в разных браузерах, используя системное свойство `browser`:

**Chrome** (по умолчанию):
```bash
mvn clean test -Dbrowser=chrome
```

**Яндекс Браузер**:
```bash
mvn clean test -Dbrowser=yandex
```

**Firefox**:
```bash
mvn clean test -Dbrowser=firefox
```

**Edge**:
```bash
mvn clean test -Dbrowser=edge
```

### Запуск конкретного теста

```bash
mvn test -Dtest=LoginTest#testSuccessfulLogin
```

С выбором браузера:
```bash
mvn test -Dtest=LoginTest#testSuccessfulLogin -Dbrowser=edge
```

## Запуск из IDE (IntelliJ IDEA, Eclipse)

### Настройка VM опций в Run Configuration

Для запуска тестов с конкретными настройками из IDE:

1. Откройте **Run** → **Edit Configurations**
2. Выберите нужный тест или создайте новую конфигурацию
3. В поле **VM options** добавьте нужные параметры:

```
-Dbrowser=firefox -Dheadless=true
```

### Примеры VM опций

**Headless Chrome:**
```
-Dbrowser=chrome -Dheadless=true
```

**Firefox с конкретным размером окна:**
```
-Dbrowser=firefox -Dwindow.size=1366x768
```

**Edge с пользовательским путем:**
```
-Dbrowser=edge -Dbrowser.binary="C:\Custom\Path\msedge.exe"
```

## Генерация и просмотр Allure отчетов

### Генерация отчета

После выполнения тестов сгенерируйте Allure отчет:

```bash
mvn allure:report
```

Отчет будет создан в директории `target/site/allure-maven-plugin/index.html`

### Просмотр отчета

#### Вариант 1: Через Maven (автоматически открывается в браузере)

```bash
mvn allure:serve
```

## Реализованные тесты

### Тест 1: Успешный логин
- **Пользователь**: `standard_user`
- **Пароль**: `secret_sauce`
- **Проверка**: Корректный переход на страницу продуктов после авторизации