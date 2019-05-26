CREATE TABLE Prowadzacy
(
  id_prowadzacego SERIAL,
  imie VARCHAR(30) NOT NULL,
  nazwisko VARCHAR(30) NOT NULL,
  mail VARCHAR(30) NOT NULL,
  CONSTRAINT id_prowadzacego_pk PRIMARY KEY (id_prowadzacego)
);

CREATE TABLE Kursy
(
  id_kursu SERIAL,
  nazwa VARCHAR(50) NOT NULL,
  liczba_godzin INT NOT NULL,
  id_prowadzacego INT NOT NULL,
  CONSTRAINT id_kursu_pk PRIMARY KEY (id_kursu)
);

CREATE TABLE Kursanci
(
  id_kursanta SERIAL,
  imie VARCHAR(30) NOT NULL,
  nazwisko VARCHAR(30) NOT NULL,
  pesel varchar(11) NOT NULL,
  miejscowosc VARCHAR(30) NOT NULL,
  ulica VARCHAR(30) NOT NULL,
  nr_domu INT NOT NULL,
  kod_pocztowy INT NOT NULL,
  nr_telefonu INT NOT NULL,
  mail VARCHAR(50) NOT NULL,
  id_kursu INT NOT NULL,
  CONSTRAINT id_kursanta PRIMARY KEY (id_kursanta),
  UNIQUE (pesel)
);

ALTER TABLE kursanci ADD CONSTRAINT max_9999 CHECK (nr_domu <= 9999);
ALTER TABLE kursanci ADD CONSTRAINT max_99999 CHECK (kod_pocztowy <= 99999);
ALTER TABLE kursanci ADD CONSTRAINT kod_pocztowy_czy_5_znakow CHECK (kod_pocztowy > 9999);
ALTER TABLE kursanci ADD CONSTRAINT max_999999999 CHECK (nr_telefonu <= 999999999);
ALTER TABLE kursanci ADD CONSTRAINT nr_telefonu_czy_9_znakow CHECK (nr_telefonu > 99999999);
ALTER TABLE kursanci ADD CONSTRAINT wieksza_od_0 CHECK (nr_domu > 0 AND kod_pocztowy > 0 AND nr_telefonu > 0);

ALTER TABLE kursy ADD CONSTRAINT wieksza_od_0 CHECK (liczba_godzin > 0);
ALTER TABLE kursy ADD CONSTRAINT max_500 CHECK (liczba_godzin <= 500);

ALTER TABLE Kursy
ADD CONSTRAINT id_prowadzacy_fk
FOREIGN KEY (id_prowadzacego) REFERENCES Prowadzacy(id_prowadzacego)
on update CASCADE;

ALTER TABLE Kursanci
ADD CONSTRAINT id_kursu_fk
FOREIGN KEY (id_kursu) REFERENCES Kursy (id_kursu)
on update CASCADE;

CREATE OR REPLACE FUNCTION dodaj(imie1 text, nazwisko1 text, pesel1 text, miejscowosc1 text, ulica1 text, nr_domu1 integer, kod_pocztowy1 integer, nr_telefonu1 integer, mail1 text, id_kursu1 integer) RETURNS integer AS $$
	declare
	local_id numeric;
	BEGIN
		
		select id_kursanta into local_id from kursanci where pesel = pesel1;
		if local_id is not null then 
			RETURN 1;
		else
			insert into Kursanci (imie, nazwisko, pesel, miejscowosc, ulica, nr_domu, kod_pocztowy, nr_telefonu, mail, id_kursu) values (imie1, nazwisko1, pesel1, miejscowosc1, ulica1, nr_domu1, kod_pocztowy1, nr_telefonu1, mail1, id_kursu1);
		END if;
		RETURN 0;
	END
$$ LANGUAGE 'plpgsql';

INSERT INTO prowadzacy (imie, nazwisko, mail) VALUES ('Adam',	'Nowak',	'adam.nowak@mail.pl');
INSERT INTO prowadzacy (imie, nazwisko, mail) VALUES ('Piotr',	'Socha',	'piotr.socha@mail.pl');
INSERT INTO prowadzacy (imie, nazwisko, mail) VALUES ('Kamil',	'Kowalski',	'kamil.kowalski@mail.pl');
INSERT INTO prowadzacy (imie, nazwisko, mail) VALUES ('Wojciech',	'Troń',	'wojciech.tron@mail.pl');
INSERT INTO prowadzacy (imie, nazwisko, mail) VALUES ('Julia',	'Nowak',	'adam.nowak@mail.pl');

INSERT INTO kursy (nazwa, liczba_godzin, id_prowadzacego) VALUES ('Rozwój osobisty',	30,	1);
INSERT INTO kursy (nazwa, liczba_godzin, id_prowadzacego) VALUES ('Doradca zawodowy',	35,	2);
INSERT INTO kursy (nazwa, liczba_godzin, id_prowadzacego) VALUES ('Opiekun w żłobku',	50,	3);
INSERT INTO kursy (nazwa, liczba_godzin, id_prowadzacego) VALUES ('Kosmetologia',	70,	5);
INSERT INTO kursy (nazwa, liczba_godzin, id_prowadzacego) VALUES ('Zarządzanie krysyzem w firmie',	120,	4);
INSERT INTO kursy (nazwa, liczba_godzin, id_prowadzacego) VALUES ('Psychologia dziecka',	50,	5);

INSERT INTO kursanci (imie, nazwisko, pesel, miejscowosc, ulica, nr_domu, kod_pocztowy, nr_telefonu, mail, id_kursu) VALUES ('Karol', 'Nowak', '93071589648', ',Rzeszów', 'Krzywoustego', 7, 35015, 123456789,	'karol.nowak@mail.pl', 1);
INSERT INTO kursanci (imie, nazwisko, pesel, miejscowosc, ulica, nr_domu, kod_pocztowy, nr_telefonu, mail, id_kursu) VALUES ('Wioletta', 'Trawa', '86022825582', 'Rzeszów', 'Graniczna', 5, 34125, 123547852, 'wioletta.trawa@mail.pl', 4);
INSERT INTO kursanci (imie, nazwisko, pesel, miejscowosc, ulica, nr_domu, kod_pocztowy, nr_telefonu, mail, id_kursu) VALUES ('Arkadiusz', 'Polak', '94021325385', 'Rzeszów', 'Rejtana', 54, 34024, 564189151, 'arkadiusz.polak@mail.pl', 2);
INSERT INTO kursanci (imie, nazwisko, pesel, miejscowosc, ulica, nr_domu, kod_pocztowy, nr_telefonu, mail, id_kursu) VALUES ('Karolina', 'Matra', '49030111843', 'Rzeszów', 'Polna', 85, 35621, 141865165, 'karolina.matra@mail.pl', 6);
INSERT INTO kursanci (imie, nazwisko, pesel, miejscowosc, ulica, nr_domu, kod_pocztowy, nr_telefonu, mail, id_kursu) VALUES ('Wojciech', 'Kula', '51020752383', 'Stalowa Wola', 'Nowa', 456, 36478, 541818454, 'wojciech.kula@mail.pl', 6);
INSERT INTO kursanci (imie, nazwisko, pesel, miejscowosc, ulica, nr_domu, kod_pocztowy, nr_telefonu, mail, id_kursu) VALUES ('Kamila', 'Nowakowska', '45071351431', 'Jarosław', '3 Maja', 8, 35000, 484165144, 'kamila.nowakowsa@mail.pl', 2);
INSERT INTO kursanci (imie, nazwisko, pesel, miejscowosc, ulica, nr_domu, kod_pocztowy, nr_telefonu, mail, id_kursu) VALUES ('Jakub', 'Puk', '55061187836', 'Nisko', 'Pułaskiego', 65, 34800, 546541165, 'jakub.puk@mail.pl', 3);
