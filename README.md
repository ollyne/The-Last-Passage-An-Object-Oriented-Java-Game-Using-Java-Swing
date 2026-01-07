# The Last Passage

## Deskripsi Singkat
The Last Passage adalah game maze 2D berbasis Java Swing yang dikembangkan untuk menerapkan konsep Object-Oriented Programming (OOP). Tujuan utama permainan ini adalah menantang pemain untuk menyelesaikan seluruh maze dalam waktu tercepat. Pemain dituntut untuk bergerak secara efisien, memilih jalur yang optimal, serta meminimalkan waktu tempuh dari awal hingga akhir permainan. Untuk mendukung tujuan tersebut, game ini dilengkapi dengan multi-level maze, sistem timer real-time dengan pause otomatis saat pergantian level, overlay informasi level selesai, serta sistem records yang mencatat dan membandingkan waktu penyelesaian pemain melalui latest records dan best record sebagai tolok ukur performa.

---

## Cara Menjalankan Aplikasi

### 1. Pastikan Java Development Kit (JDK) Terpasang
Gunakan JDK versi 8 atau lebih baru. Pastikan Java sudah terinstal dan terdeteksi dengan benar pada sistem.

### 2. Buka Project The Last Passage
Ekstrak file ZIP project, kemudian buka folder project menggunakan IDE Java seperti Eclipse.

### 3. Periksa Struktur Project
Pastikan struktur project sebagai berikut:
- Folder `src` berisi package `main`, `panel`, `logic`, `entity`, dan `data`
- Folder `assets` tersedia
- File `scores.txt` berada di luar folder `src`

Jika IDE meminta pengaturan source folder, tetapkan folder `src` sebagai Source Folder agar seluruh package dikenali dengan benar.

### 4. Jalankan Aplikasi
Buka file berikut:
src/main/MainApp.java
Lalu jalankan sebagai Java Application.

### 5. Menggunakan Aplikasi
Setelah aplikasi dijalankan, menu utama akan ditampilkan dengan fitur berikut:
- Start Game untuk memulai permainan
- Records untuk menampilkan latest records dan best record
- Exit untuk menutup aplikasi

Pemain mengendalikan karakter untuk menyelesaikan setiap maze secepat mungkin.

---

## Daftar Package, Class, dan Fungsinya
Aplikasi The Last Passage terdiri dari 5 package utama dengan total 12 class. Struktur ini dirancang untuk menghasilkan program yang modular, terorganisir, dan sesuai dengan prinsip Object-Oriented Programming.

### PACKAGE: main
- MainApp  
  Entry point aplikasi untuk menjalankan game.

### PACKAGE: logic
- GameState  
  Menyimpan state permainan, yaitu MENU, PLAYING, dan RECORDS.
- SceneManager  
  Mengatur perpindahan antar panel atau scene.

### PACKAGE: panel
- GameFrame  
  Frame utama aplikasi yang menampung seluruh panel.
- MenuPanel  
  Menampilkan menu utama (Play, Records, Exit).
- GamePanel  
  Mengelola gameplay utama, maze, timer, dan overlay level.
- RecordsPanel  
  Menampilkan latest records dan best record.

### PACKAGE: entity
- GameObject  
  Class dasar (abstract) untuk seluruh objek dalam game.
- Player  
  Mengatur posisi, pergerakan, dan status pemain.
- Item  
  Class dasar (abstract) untuk item dalam game.
- KeyItem  
  Item kunci yang dibutuhkan untuk menyelesaikan maze.

### PACKAGE: data
- GameDataManager  
  Mengelola penyimpanan dan pembacaan data skor dari file.
- Record  
  Merepresentasikan satu data record berupa nama pemain dan waktu penyelesaian.

---

## Penjelasan Konsep OOP pada Aplikasi

Aplikasi The Last Passage dikembangkan dengan menerapkan konsep Object-Oriented Programming (OOP) untuk memastikan struktur program yang terorganisir, modular, dan mudah dikembangkan. Konsep OOP yang digunakan meliputi Encapsulation, Inheritance, Abstraction, dan Polymorphism.

### 1. Encapsulation
Encapsulation diterapkan dengan membungkus data dan perilaku ke dalam class serta membatasi akses langsung terhadap data menggunakan access modifier seperti private dan protected.  

Sebagai contoh, class Player menyimpan data posisi dan status pemain sebagai atribut private. Akses terhadap data tersebut dilakukan melalui method seperti getRow(), getCol(), dan hasKey(), sehingga integritas data tetap terjaga dan perubahan state dapat dikontrol.

### 2. Inheritance
Inheritance digunakan untuk membentuk hubungan hierarki antar class sehingga class turunan dapat mewarisi atribut dan method dari class induknya. Pendekatan ini mengurangi duplikasi kode dan meningkatkan reusabilitas.

Implementasi inheritance dalam aplikasi ini adalah:
- Class Player mewarisi class abstrak GameObject
- Class KeyItem mewarisi class abstrak Item

### 3. Abstraction
Abstraction diterapkan melalui penggunaan abstract class, yaitu GameObject dan Item. Abstract class digunakan untuk mendefinisikan perilaku umum tanpa memberikan detail implementasi.

Sebagai contoh, method draw(Graphics g) dideklarasikan pada abstract class dan kemudian diimplementasikan secara spesifik pada class Player dan KeyItem. Dengan pendekatan ini, detail implementasi disembunyikan, sementara struktur dan perilaku umum tetap terdefinisi dengan jelas.

### 4. Polymorphism
Polymorphism memungkinkan objek dari class yang berbeda diperlakukan sebagai objek dari class induknya, tetapi tetap menjalankan perilaku sesuai dengan implementasi masing-masing.

Dalam aplikasi ini, objek Player dan KeyItem dapat diperlakukan sebagai GameObject atau Item, namun pemanggilan method draw() akan menjalankan implementasi sesuai dengan class aslinya.

---

## Prinsip Tambahan
Selain empat konsep utama OOP, aplikasi ini juga menerapkan prinsip Separation of Concerns melalui pemisahan tanggung jawab ke dalam package yang berbeda. Package entity menangani representasi objek game, panel mengelola antarmuka pengguna, logic mengatur alur dan state permainan, serta data bertanggung jawab atas penyimpanan dan pengelolaan data.

Pendekatan ini membuat kode lebih mudah dipahami, dipelihara, dan dikembangkan di masa depan.

---

## Kesimpulan
Penerapan konsep Object-Oriented Programming pada aplikasi The Last Passage menghasilkan struktur program yang modular, terorganisir, dan scalable. Dengan memanfaatkan Encapsulation, Inheritance, Abstraction, dan Polymorphism secara konsisten, aplikasi ini tidak hanya memenuhi kriteria tugas, tetapi juga mencerminkan praktik pemrograman berorientasi objek yang baik.
