# Rencana Struktur Kelas untuk Sistem Manajemen Jendela Kustom JavaFX

Berikut adalah ringkasan komponen yang perlu dibuat untuk membangun sistem manajemen jendela kustom yang robust di JavaFX.

## 1. Package `id.alphareso.meidofx.base.controls`

* **Deskripsi:** Menyediakan kelas untuk tombol kendali jendela (minimize, maximize, close) dengan tampilan yang dapat disesuaikan melalui style guide dan enumerator. Tombol-tombol ini harus independen dan dapat digunakan di berbagai tempat.
* **Referensi Ikon:**
    * `images/close-light.png`
    * `images/minimize-light.png`
    * `images/maximize-light.png`
* **Kelas yang Dibutuhkan:**
    * `WindowControlButton.java` (Kelas untuk setiap tombol kendali)
    * Potensi kelas `WindowControlsSkin.java` untuk kustomisasi tampilan (jika diperlukan kompleksitas lebih lanjut).

## 2. Package `id.alphareso.meidofx.base.enums`

* **Deskripsi:** Menyediakan kelas enum dengan style guide untuk mendefinisikan berbagai konfigurasi tampilan untuk title bar.
* **Pilihan Konfigurasi:**
    * `ALL`: Menampilkan semua komponen (ikon, judul, dan tombol kendali).
    * `NO_LEFT`: Hanya menampilkan tombol kendali.
    * `NO_RIGHT`: Hanya menampilkan ikon dan judul.
    * `NO_ALL`: Tidak menampilkan apa-apa.
* **Kelas yang Dibutuhkan:**
    * `TitleBarStyle.java` (Kelas Enum)
* **Contoh Penggunaan:**
    ```java
    //properti
    public static RoundStage stage;
    //inisiasi
    TitleBar titleBar = new TitleBar(Main.stage, TitleBarStyle.ALL);
    ```

## 3. Package `id.alphareso.meidofx.base.handlers`

* **Deskripsi:** Menyediakan kelas dengan style guide untuk menangani interaksi pengguna pada jendela.
* **Fungsionalitas:**
    * Drag and drop pada title bar untuk memindahkan jendela aplikasi.
    * Resize jendela dari arah kanan bawah.
* **Referensi Ikon Resize:**
    ```svg
    <path d="M542.72 884.053333l341.333333-341.333333a32 32 0 0 1 47.445334 42.816l-2.197334 2.432-341.333333 341.333333a32 32 0 0 1-47.466667-42.837333l2.197334-2.432 341.333333-341.333333-341.333333 341.333333z m-437.333333-10.666666l778.666666-778.666667a32 32 0 0 1 47.445334 42.816l-2.197334 2.432-778.666666 778.666667a32 32 0 0 1-47.466667-42.837334l2.197333-2.432 778.666667-778.666666-778.666667 778.666666z"/>
    ```
* **Kelas yang Dibutuhkan:**
    * `TitleBarDragHandler.java`
    * `BottomRightResizeHandler.java`

## 4. Package `id.alphareso.meidofx.base.sidebars`

* **Deskripsi:** Menyediakan panel navigasi vertikal berbasis `StackPane` dan `VBox`. Panel ini harus memiliki tinggi penuh, lebar default 200 (dapat disesuaikan), dan mendukung item navigasi berikon dan berakar.
* **Fitur:** Builder untuk memudahkan penambahan atau pengurangan menu item.
* **Referensi Ikon:** Gunakan ikon SVG yang relevan.
* **Kelas yang Dibutuhkan:**
    * `SideBar.java`
    * `SideBarItem.java`
    * `SideBarBuilder.java` (atau metode builder di dalam `SideBar`)

## 5. Package `id.alphareso.meidofx.base.stages`

* **Deskripsi:** Menyediakan kelas dasar untuk semua jendela aplikasi dan implementasi jendela dengan sudut membulat.
* **Kelas yang Dibutuhkan:**
    * `BaseStage.java`: Mewarisi dari `javafx.stage.Stage`.
    * `RoundStage.java`: Mewarisi dari `BaseStage`, berbasis `StackPane` dengan sudut membulat 20px. Fitur yang diharapkan:
        * Title bar (berbasis `StackPane`).
        * Sidebar (berbasis `StackPane`).
        * Area konten dinamis (berbasis `StackPane`).
    * **Catatan:** `RoundStage` sebagai subclass dari `BaseStage` pada dasarnya adalah `StackPane` dengan sudut membulat 20px dan dapat digunakan untuk berbagai keperluan (aplikasi utama, modal, notifikasi, dll.).

## 6. Package `id.alphareso.meidofx.base.titles`

* **Deskripsi:** Menyediakan kelas dengan style guide untuk title bar berbasis `StackPane` yang dapat disesuaikan gayanya dengan ikon, teks judul, dan tombol kendali jendela (menggunakan komponen dari package `controls`).
* **Fitur:**
    * Tampilan independen yang dapat diuji.
    * Penggunaan properti judul dari `javafx.stage.Stage` untuk menghindari konflik dan memudahkan inisiasi di aplikasi utama.
* **Kelas yang Dibutuhkan:**
    * `TitleBar.java`

## Prinsip Desain

Semua kelas ini harus dirancang dengan mempertimbangkan prinsip **SOLID** (Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion) dan **DRY** (Don't Repeat Yourself) untuk menciptakan sistem jendela yang kohesif, dapat disesuaikan, dan mudah dipelihara.