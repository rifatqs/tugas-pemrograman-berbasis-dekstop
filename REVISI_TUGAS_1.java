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

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        //data menu (array)
        Menu[] daftarMenu = {
            new Menu("Kepiting", 25000, "Makanan"),
            new Menu("Ikan Bakar", 30000, "Makanan"),
            new Menu("Salmon", 28000, "Makanan"),
            new Menu("Lobster", 40000, "Makanan"),
            new Menu("Es Teh", 8000, "Minuman"),
            new Menu("Es Jeruk", 10000, "Minuman"),
            new Menu("Kopi Hitam", 12000, "Minuman"),
            new Menu("Jus Alpukat", 15000, "Minuman")
        };

        //array untuk pesanan maks 4
        Menu[] pesanan = new Menu[4];
        int[] jumlah = new int[4];

        //menampilkan menu
        tampilkanMenu(daftarMenu);

        //input jumlah pesanan
        System.out.print("Masukkan jumlah menu yang ingin dipesan (1-4): ");
        int jmlPesanan = in.nextInt();
        in.nextLine(); 

        //terima & olah pesanan
        if (jmlPesanan >= 1) {
            System.out.print("Pesanan 1 (Nama Menu = jumlah): ");
            String baris = in.nextLine();
            prosesPesanan(baris, daftarMenu, pesanan, jumlah, 0);
        }
        if (jmlPesanan >= 2) {
            System.out.print("Pesanan 2 (Nama Menu = jumlah): ");
            String baris = in.nextLine();
            prosesPesanan(baris, daftarMenu, pesanan, jumlah, 1);
        }
        if (jmlPesanan >= 3) {
            System.out.print("Pesanan 3 (Nama Menu = jumlah): ");
            String baris = in.nextLine();
            prosesPesanan(baris, daftarMenu, pesanan, jumlah, 2);
        }
        if (jmlPesanan >= 4) {
            System.out.print("Pesanan 4 (Nama Menu = jumlah): ");
            String baris = in.nextLine();
            prosesPesanan(baris, daftarMenu, pesanan, jumlah, 3);
        }

        //hitung subtotal
        double subtotal = 0;
        if (pesanan[0] != null) subtotal += pesanan[0].harga * jumlah[0];
        if (pesanan[1] != null) subtotal += pesanan[1].harga * jumlah[1];
        if (pesanan[2] != null) subtotal += pesanan[2].harga * jumlah[2];
        if (pesanan[3] != null) subtotal += pesanan[3].harga * jumlah[3];

        double pajak = subtotal * 0.10;
        double biayaPelayanan = 20000;
        double totalSebelumPotongan = subtotal + pajak + biayaPelayanan;

        double diskon = 0;
        double potonganPromo = 0;
        boolean adaDiskon = false;
        boolean adaPromoMinuman = false;

        //diskon & promo
        if (subtotal > 100000) {
            diskon = totalSebelumPotongan * 0.10;
            adaDiskon = true;
        } else if (subtotal > 50000) {
            potonganPromo = hitungPromoMinuman(pesanan, jumlah);
            if (potonganPromo > 0) {
                adaPromoMinuman = true;
            }
        }

        double totalBayar = totalSebelumPotongan - diskon - potonganPromo;

        //cetak struk
        cetakStruk(pesanan, jumlah, subtotal, pajak, biayaPelayanan,
                   diskon, potonganPromo, adaDiskon, adaPromoMinuman, totalBayar);

        in.close();
    }

    //menampilkan menu
    static void tampilkanMenu(Menu[] m) {
        System.out.println("+++++ DAFTAR MENU RESTORAN +++++\n");

        System.out.println(">> Makanan:");
        System.out.println("- " + m[0].nama + " - Rp " + (int)m[0].harga);
        System.out.println("- " + m[1].nama + " - Rp " + (int)m[1].harga);
        System.out.println("- " + m[2].nama + " - Rp " + (int)m[2].harga);
        System.out.println("- " + m[3].nama + " - Rp " + (int)m[3].harga);

        System.out.println("\n>> Minuman:");
        System.out.println("- " + m[4].nama + " - Rp " + (int)m[4].harga);
        System.out.println("- " + m[5].nama + " - Rp " + (int)m[5].harga);
        System.out.println("- " + m[6].nama + " - Rp " + (int)m[6].harga);
        System.out.println("- " + m[7].nama + " - Rp " + (int)m[7].harga);

        System.out.println("\nFormat input pesanan: Nama Menu = jumlah");
        System.out.println("Contoh: Kepiting = 2\n");
    }

    //memproses satu baris pesanan: nama menu = jumlah
    static void prosesPesanan(String baris, Menu[] daftarMenu,
                              Menu[] pesanan, int[] jumlah, int index) {
        String[] bagian = baris.split("=");
        if (bagian.length != 2) {
            System.out.println("Format salah. Gunakan: Nama Menu = jumlah");
            return;
        }

        String namaMenu = bagian[0].trim();
        String jumlahStr = bagian[1].trim();

        Menu m = cariMenu(namaMenu, daftarMenu);
        if (m == null) {
            System.out.println("Menu \"" + namaMenu + "\" tidak ditemukan.");
            return;
        }

        try {
            int jml = Integer.parseInt(jumlahStr);
            pesanan[index] = m;
            jumlah[index] = jml;
        } catch (NumberFormatException e) {
            System.out.println("Jumlah tidak valid.");
        }
    }

    static Menu cariMenu(String nama, Menu[] d) {
        if (nama.equalsIgnoreCase(d[0].nama)) return d[0];
        if (nama.equalsIgnoreCase(d[1].nama)) return d[1];
        if (nama.equalsIgnoreCase(d[2].nama)) return d[2];
        if (nama.equalsIgnoreCase(d[3].nama)) return d[3];
        if (nama.equalsIgnoreCase(d[4].nama)) return d[4];
        if (nama.equalsIgnoreCase(d[5].nama)) return d[5];
        if (nama.equalsIgnoreCase(d[6].nama)) return d[6];
        if (nama.equalsIgnoreCase(d[7].nama)) return d[7];
        return null;
    }

    // promo beli 1 gratis 1 untuk minuman 
    static double hitungPromoMinuman(Menu[] p, int[] j) {
        double potongan = 0;

        if (p[0] != null && p[0].kategori.equalsIgnoreCase("Minuman")) {
            potongan += (j[0] / 2) * p[0].harga;
        }
        if (p[1] != null && p[1].kategori.equalsIgnoreCase("Minuman")) {
            potongan += (j[1] / 2) * p[1].harga;
        }
        if (p[2] != null && p[2].kategori.equalsIgnoreCase("Minuman")) {
            potongan += (j[2] / 2) * p[2].harga;
        }
        if (p[3] != null && p[3].kategori.equalsIgnoreCase("Minuman")) {
            potongan += (j[3] / 2) * p[3].harga;
        }

        return potongan;
    }

    //cetak struk pesanan
    static void cetakStruk(Menu[] p, int[] j,
                           double subtotal, double pajak, double pelayanan,
                           double diskon, double potonganPromo,
                           boolean adaDiskon, boolean adaPromoMinuman,
                           double totalBayar) {

        System.out.println("\n+++++++ STRUK PEMBAYARAN +++++++");
        System.out.println("Pesanan:");

        if (p[0] != null) {
            double total = p[0].harga * j[0];
            System.out.println("- " + p[0].nama + " x" + j[0] +
                               " @Rp " + (int)p[0].harga +
                               " = Rp " + (int)total);
        }
        if (p[1] != null) {
            double total = p[1].harga * j[1];
            System.out.println("- " + p[1].nama + " x" + j[1] +
                               " @Rp " + (int)p[1].harga +
                               " = Rp " + (int)total);
        }
        if (p[2] != null) {
            double total = p[2].harga * j[2];
            System.out.println("- " + p[2].nama + " x" + j[2] +
                               " @Rp " + (int)p[2].harga +
                               " = Rp " + (int)total);
        }
        if (p[3] != null) {
            double total = p[3].harga * j[3];
            System.out.println("- " + p[3].nama + " x" + j[3] +
                               " @Rp " + (int)p[3].harga +
                               " = Rp " + (int)total);
        }

        System.out.println("--------------------------------------");
        System.out.println("Subtotal        : Rp " + (int)subtotal);
        System.out.println("Pajak (10%)     : Rp " + (int)pajak);
        System.out.println("Biaya Pelayanan : Rp " + (int)pelayanan);

        if (adaDiskon) {
            System.out.println("Diskon 10%      : -Rp " + (int)diskon);
        } else if (adaPromoMinuman) {
            System.out.println("Promo Minuman   : -Rp " + (int)potonganPromo +
                               " (Beli 1 gratis 1)");
        } else {
            System.out.println("Diskon/Promo    : Tidak ada");
        }

        System.out.println("--------------------------------------");
        System.out.println("Total Bayar     : Rp " + (int)totalBayar);
        System.out.println("======================================");
        System.out.println("---Terima kasih---");
    }
}
