# eshop-adpro

## Modul 1
### Reflection 1
Melalui tutorial ini, penulisan kode yang baik menurut saya sebagai berikut:
1. Penamaan Variabel yang Jelas Memberikan nama variabel yang jelas dan sesuai dengan kegunaan sangat penting untuk meningkatkan keterbacaan dan pemahaman kode. Penggunaan nama variabel yang ambigu dapat membingungkan programmer lain yang membaca.
2. Penggunaan Komentar yang tidak berlebihan Komentar sebaiknya hanya digunakan untuk menjelaskan alasan di balik suatu implementasi. Jika kode ditulis dengan baik, maka seharusnya sudah cukup jelas tanpa perlu komentar yang berlebihan. Kode yang baik seharusnya bisa dibaca dan mudah dipahami.
3. Menghindari Duplikasi Kode dengan Memanfaatkan Fungsi yang Ada Daripada menulis kode yang berulang, lebih baik memanfaatkan fungsi yang sudah ada untuk menghindari duplikasi kode. Dengan begitu, kode menjadi lebih reusable, mudah dikelola, dan mengurangi kemungkinan bug. 
4. Menerapkan Prinsip Single Responsibility Principle (SRP) Berdasarkan prinsip SRP, setiap kelas atau fungsi hanya boleh memiliki satu alasan untuk berubah. Artinya, setiap kelas harus memiliki satu tanggung jawab spesifik. Hal ini terlihat dalam struktur kode yang telah dibuat, di mana terdapat kelas repository dan service yang masing-masing memiliki tanggung jawabnya sendiri.

Namun masih ada hal yag perlu ditingkatkan dalam kode seperti Validasi Input. Perlu ditambahkan validasi input, seperti memastikan bahwa nama produk tidak boleh kosong, kuantitas tidak boleh bernilai negatif, dan sebagainya. Ini dapat dilakukan dengan menambahkan anotasi @NotBlank, @Min(0), atau validasi lain yang sesuai di dalam kode.

### Reflection 2
1. Setelah menulis unit test, saya semakin menyadari pentingnya pengujian, terutama dalam skala enterprise. Unit testing membantu mengurangi bug dengan mendorong developer mempertimbangkan berbagai skenario yang mungkin terjadi. Jumlah unit test yang dibutuhkan tergantung pada kompleksitas program. Yang terpenting adalah menguji setiap method, termasuk skenario utama, edge cases, dan error handling. Jika semua skenario telah diuji, maka pengujian dapat dianggap cukup. Code coverage menunjukkan sejauh mana kode diuji, tetapi mencapai 100% coverage tidak menjamin bahwa kode bebas dari bug. Code coverage hanya mengukur eksekusi kode, bukan kebenaran logika program. Setelah melalukan unit test, saya lebih yakin bahwa kode telah memenuhi fungsinya dan lebih siap menghadapi proses integrasi.

2. Kode tersebut bukan clean code karena functional test class baru memiliki setup dan variabel yang sama dan juga melanggar prinsip DRY. Solusinya, buat kelas terpisah untuk menangani setup logic, lalu gunakan inheritance agar lebih reusable. Masalah yang mungkin muncul di antaranya duplikasi setup & URL → Gunakan helper method agar struktur kode lebih rapi dan mudah dipelihara. magic string & variabel tidak konsisten → Gunakan konstanta atau test fixture agar perubahan hanya perlu dilakukan di satu tempat. Assertion kurang spesifik → Tambahkan validasi isi dan urutan data agar pengujian lebih akurat.
Dengan perbaikan ini, kode menjadi lebih bersih, terstruktur, dan efektif dalam mendeteksi bug.

## Modul 2
1. > Unused import 'org.springframework.web.bind.annotation.*', 'org.springframework.web.bind.annotation.DeleteMapping', dan 'org.springframework.web.bind.annotation.PatchMapping' pada ProductController. 
- Hal ini terjadi karena import org.springframework.web.bind.annotation.* yang tidak dibutuhkan (tidak dipakai). Solusi/strategi saya adalah dengan hanya menggunakan import-import yang diperlukan, sehingga tidak menyebabkan redundansi.
perbaikannya seperti berikut.
```
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
  ```
2. Dalam proyek ini, saya menerapkan _Continuous Integration_ (CI) dengan merancang workflow `ci.yml`. Proses ini secara otomatis menjalankan seluruh unit test setiap kali ada push atau pull request. Setelah itu, kode akan dipindai dan dianalisis menggunakan PMD untuk menilai kualitasnya serta mengidentifikasi _best practices_ yang dapat diterapkan. Setelah tahap ini selesai, proyek akan berlanjut ke tahap _Continuous Deployment/Delivery_, di mana Koyeb digunakan sebagai platform untuk _deployment_ otomatis. 