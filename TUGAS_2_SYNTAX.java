import java.util.Scanner;

class Menu {                                
    String nama;
    double harga;
    String kategori; 

    Menu(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }
}

public class Main {

    static Scanner in = new Scanner(System.in); 
    static Menu[] daftarMenu = new Menu[100];
    static int jumlahMenu = 0;

    public static void main(String[] args) {        
        initMenuAwal();

        boolean x = true;
        while (x) {
            System.out.println("\n+++ APLIKASI RESTORAN +++");
            System.out.println("1. Menu Pelanggan (Pemesanan)");
            System.out.println("2. Menu Pengelola Restoran");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu: ");
            String pilih = in.nextLine();

            if (pilih.equals("1")) {
                menuPelanggan();
            } else if (pilih.equals("2")) {
                menuPengelola();
            } else if (pilih.equals("3")) {
                System.out.println("Terima kasih. Program selesai.");
                x = false;
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }

        in.close();
    }

    static void initMenuAwal() {
        daftarMenu[jumlahMenu++] = new Menu("Kepiting", 25000, "Makanan");
        daftarMenu[jumlahMenu++] = new Menu("Ikan Bakar", 30000, "Makanan");
        daftarMenu[jumlahMenu++] = new Menu("Salmon", 28000, "Makanan");
        daftarMenu[jumlahMenu++] = new Menu("Lobster", 40000, "Makanan");
        daftarMenu[jumlahMenu++] = new Menu("Es Teh", 8000, "Minuman");
        daftarMenu[jumlahMenu++] = new Menu("Es Jeruk", 10000, "Minuman");
        daftarMenu[jumlahMenu++] = new Menu("Kopi Hitam", 12000, "Minuman");
        daftarMenu[jumlahMenu++] = new Menu("Jus Alpukat", 15000, "Minuman");
    }

    //MENU PELANGGAN
    static void menuPelanggan() {
        if (jumlahMenu == 0) {
            System.out.println("Belum ada menu.");
            return;
        }

        int[] indexPesanan = new int[100];
        int[] jumlahPesanan = new int[100];
        int jmlItem = 0;

        System.out.println("\n+++ MENU PELANGGAN +++");
        tampilkanMenu();

        System.out.println("\nKetik 'selesai' untuk mengakhiri pemesanan.");

        while (true) {
            System.out.print("\nNama menu (atau 'selesai'): ");
            String nama = in.nextLine();

            if (nama.equalsIgnoreCase("selesai")) {
                break;
            }

            int idx = cariMenuByNama(nama);
            if (idx == -1) {
                System.out.println("Menu tidak ditemukan, coba lagi.");
                continue;
            }

            System.out.print("Jumlah: ");
            int qty = Integer.parseInt(in.nextLine());

            if (qty <= 0) {
                System.out.println("Jumlah harus > 0.");
                continue;
            }

            indexPesanan[jmlItem] = idx;
            jumlahPesanan[jmlItem] = qty;
            jmlItem++;

            System.out.println("Ditambahkan: " + daftarMenu[idx].nama + " x" + qty);
        }

        if (jmlItem == 0) {
            System.out.println("Tidak ada pesanan.");
            return;
        }

        cetakStruk(indexPesanan, jumlahPesanan, jmlItem);
    }

    //MENU PENGELOLA 
    static void menuPengelola() {
        boolean kembali = false;
        while (!kembali) {
            System.out.println("\n+++ MENU PENGELOLA RESTORAN +++");
            System.out.println("1. Tampilkan Daftar Menu");
            System.out.println("2. Tambah Menu Baru");
            System.out.println("3. Ubah Harga Menu");
            System.out.println("4. Hapus Menu");
            System.out.println("5. Kembali");
            System.out.print("Pilih: ");
            String pilih = in.nextLine();

            if (pilih.equals("1")) {
                tampilkanMenu();
            } else if (pilih.equals("2")) {
                tambahMenuBaru();
            } else if (pilih.equals("3")) {
                ubahHargaMenu();
            } else if (pilih.equals("4")) {
                hapusMenu();
            } else if (pilih.equals("5")) {
                kembali = true;
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }
    }

    //FUNGSI MENU 
    static void tampilkanMenu() {
        System.out.println("\n+++ DAFTAR MENU +++");
        System.out.println(">> Makanan:");
        for (int i = 0; i < jumlahMenu; i++) {
            if (daftarMenu[i].kategori.equalsIgnoreCase("Makanan")) {
                System.out.println((i + 1) + ". " + daftarMenu[i].nama +
                        " - Rp " + (int) daftarMenu[i].harga);
            }
        }

        System.out.println("\n>> Minuman:");
        for (int i = 0; i < jumlahMenu; i++) {
            if (daftarMenu[i].kategori.equalsIgnoreCase("Minuman")) {
                System.out.println((i + 1) + ". " + daftarMenu[i].nama +
                        " - Rp " + (int) daftarMenu[i].harga);
            }
        }
    }

    static int cariMenuByNama(String nama) {
        for (int i = 0; i < jumlahMenu; i++) {
            if (daftarMenu[i].nama.equalsIgnoreCase(nama)) {
                return i;
            }
        }
        return -1;
    }

    static void tambahMenuBaru() {
        System.out.print("\nNama menu baru: ");
        String nama = in.nextLine();

        System.out.print("Harga: ");
        double harga = Double.parseDouble(in.nextLine());

        System.out.print("Kategori (1 = Makanan, 2 = Minuman): ");
        String pilih = in.nextLine();
        String kategori = pilih.equals("1") ? "Makanan" : "Minuman";

        daftarMenu[jumlahMenu++] = new Menu(nama, harga, kategori);
        System.out.println("Menu baru ditambahkan.");
    }

    static void ubahHargaMenu() {
        if (jumlahMenu == 0) {
            System.out.println("Belum ada menu.");
            return;
        }

        tampilkanMenu();
        System.out.print("\nNomor menu yang diubah: ");
        int no = Integer.parseInt(in.nextLine());

        if (no < 1 || no > jumlahMenu) {
            System.out.println("Nomor tidak valid.");
            return;
        }

        Menu m = daftarMenu[no - 1];
        System.out.println("Menu: " + m.nama + " (Rp " + (int)m.harga + ")");
        System.out.print("Harga baru: ");
        double hargaBaru = Double.parseDouble(in.nextLine());

        System.out.print("Yakin ubah? (Ya/Tidak): ");
        String konfirm = in.nextLine();

        if (konfirm.equalsIgnoreCase("Ya")) {
            m.harga = hargaBaru;
            System.out.println("Harga berhasil diubah.");
        } else {
            System.out.println("Perubahan dibatalkan.");
        }
    }

    static void hapusMenu() {
        if (jumlahMenu == 0) {
            System.out.println("Belum ada menu.");
            return;
        }

        tampilkanMenu();
        System.out.print("\nNomor menu yang dihapus: ");
        int no = Integer.parseInt(in.nextLine());

        if (no < 1 || no > jumlahMenu) {
            System.out.println("Nomor tidak valid.");
            return;
        }

        Menu m = daftarMenu[no - 1];
        System.out.println("Menu: " + m.nama + " - Rp " + (int)m.harga);
        System.out.print("Yakin hapus? (Ya/Tidak): ");
        String konfirm = in.nextLine();

        if (konfirm.equalsIgnoreCase("Ya")) {
            for (int i = no - 1; i < jumlahMenu - 1; i++) {
                daftarMenu[i] = daftarMenu[i + 1];
            }
            daftarMenu[jumlahMenu - 1] = null;
            jumlahMenu--;
            System.out.println("Menu berhasil dihapus.");
        } else {
            System.out.println("Penghapusan dibatalkan.");
        }
    }

    // STRUK & PERHITUNGAN 
    static void cetakStruk(int[] indexPesanan, int[] jumlahPesanan, int jmlItem) {
        double subtotal = 0;

        for (int i = 0; i < jmlItem; i++) {
            Menu m = daftarMenu[indexPesanan[i]];
            int qty = jumlahPesanan[i];
            subtotal += m.harga * qty;
        }

        double pajak = subtotal * 0.10;
        double pelayanan = 20000;
        double totalSblmPotongan = subtotal + pajak + pelayanan;

        double diskon = 0;
        double promoMinuman = 0;

        if (subtotal > 100000) {
            diskon = totalSblmPotongan * 0.10;
        } else if (subtotal > 50000) {
            promoMinuman = hitungPromoMinuman(indexPesanan, jumlahPesanan, jmlItem);
        }

        double totalAkhir = totalSblmPotongan - diskon - promoMinuman;

        System.out.println("\n+++ STRUK PEMBAYARAN +++");
        System.out.println("Pesanan:");

        for (int i = 0; i < jmlItem; i++) {
            Menu m = daftarMenu[indexPesanan[i]];
            int qty = jumlahPesanan[i];
            double totalPerItem = m.harga * qty;
            System.out.println("- " + m.nama + " x" + qty +
                    " @Rp " + (int)m.harga +
                    " = Rp " + (int)totalPerItem);
        }

        System.out.println("----------------------------");
        System.out.println("Subtotal      : Rp " + (int)subtotal);
        System.out.println("Pajak 10%     : Rp " + (int)pajak);
        System.out.println("Pelayanan     : Rp " + (int)pelayanan);

        if (diskon > 0) {
            System.out.println("Diskon 10%    : -Rp " + (int)diskon);
        } else if (promoMinuman > 0) {
            System.out.println("Promo Minuman : -Rp " + (int)promoMinuman + " (Beli 1 gratis 1)");
        } else {
            System.out.println("Diskon/Promo  : Tidak ada");
        }

        System.out.println("----------------------------");
        System.out.println("Total Bayar   : Rp " + (int)totalAkhir);
        System.out.println("Terima kasih!\n");
    }

    // promo beli 1 gratis 1 minuman
    static double hitungPromoMinuman(int[] indexPesanan, int[] jumlahPesanan, int jmlItem) {
        double potongan = 0;
        for (int i = 0; i < jmlItem; i++) {
            Menu m = daftarMenu[indexPesanan[i]];
            int qty = jumlahPesanan[i];
            if (m.kategori.equalsIgnoreCase("Minuman")) {
                int gratis = qty / 2;       // beli 2 gratis 1, beli 4 gratis 2, dst
                potongan += gratis * m.harga;
            }
        }
        return potongan;
    }
}
