– Arhitektura i pokretanje

U projektu sam  koristila MVVM (Model-View-ViewModel) arhitekturu.
Retrofit + Coroutines + Flow za asinhrono preuzimanje podataka sa DummyJSON API-ja.
Room za lokalno čuvanje omiljenih proizvoda na ekranu FavoritesFragment.
Hilt za Dependency Injection, jer je jednostavniji za implementaciju i dovoljan za zadatak ovog obima.
XML layout-i + Material Design smernice za UI.

-Tehničke komponente

AllProductsFragment + AllProductsViewModel – prikaz liste proizvoda sa paginacijom.
ProductDetailsFragment + ProductDetailsViewModel – detalji proizvoda na koji se klikne.
FavoritesFragment + FavoritesViewModel – lokalno čuvanje i prikaz omiljenih proizvoda.
ProductRepository – izvor podataka, bilo da dolaze sa API-a ili iz baze.

- Pokretanje projekta

Clone repozitorijum sa komandom:

git clone <https://github.com/jelenaradeka87/TehnickiZadatak-Apoteka>

Otvoriti u Android Studiu.
Sinhronizovati Gradle i pokrenuti emulator ili povezan Android uređaj.
App će prikazati listu proizvoda, klik na proizvod vodi do detalja, a dugme za favorite čuva proizvod lokalno.
