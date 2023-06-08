-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 18 Sty 2023, 03:08
-- Wersja serwera: 10.4.25-MariaDB
-- Wersja PHP: 7.4.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `market`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `bought`
--

CREATE TABLE `bought` (
  `id_zakupu` int(11) NOT NULL,
  `id_kupujacego` int(11) NOT NULL,
  `id_wydarzenia` int(11) NOT NULL,
  `ile_sztuk` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `bought`
--

INSERT INTO `bought` (`id_zakupu`, `id_kupujacego`, `id_wydarzenia`, `ile_sztuk`) VALUES
(8, 3, 9, 2),
(9, 1, 11, 3),
(10, 3, 10, 5),
(11, 3, 11, 3);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `events`
--

CREATE TABLE `events` (
  `id_eventu` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `city` varchar(100) NOT NULL,
  `date` date NOT NULL,
  `price` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `id_user` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `events`
--

INSERT INTO `events` (`id_eventu`, `name`, `city`, `date`, `price`, `amount`, `id_user`) VALUES
(9, 'Metalica', 'Warszawa', '2023-06-15', 500, 28, 3),
(10, 'Lil Nas X', 'Kraków', '2023-01-12', 300, 15, 2),
(11, 'Akon', 'Gdynia', '2024-06-12', 300, 170, 2),
(12, 'Eminem', 'San Sebastian', '2023-04-13', 900, 100, 1),
(13, 'Metalica', 'Warszawa', '2023-08-16', 777, 28, 3),
(14, 'Sanah', 'Kraków', '2023-05-18', 800, 22, 3);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `type` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `email`, `type`) VALUES
(1, 'sebastian', '123456', 's.kolanski0@gmail.com', 'admin'),
(2, 'hej', 'hej', 'hej', 'admin'),
(3, '', '', '', 'admin'),
(7, 'ooo', 'ooo', 'ooo', 'user'),
(8, 'paulina', '12345', 'Paulina@onet.pl', 'user'),
(9, 'admin', 'admin', 'admin', 'user');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `bought`
--
ALTER TABLE `bought`
  ADD PRIMARY KEY (`id_zakupu`),
  ADD KEY `id_wydarzenia` (`id_wydarzenia`),
  ADD KEY `id_kupujacego` (`id_kupujacego`);

--
-- Indeksy dla tabeli `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`id_eventu`),
  ADD KEY `id_eventu` (`id_eventu`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD KEY `user_id` (`user_id`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `bought`
--
ALTER TABLE `bought`
  MODIFY `id_zakupu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT dla tabeli `events`
--
ALTER TABLE `events`
  MODIFY `id_eventu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `bought`
--
ALTER TABLE `bought`
  ADD CONSTRAINT `bought_ibfk_1` FOREIGN KEY (`id_wydarzenia`) REFERENCES `events` (`id_eventu`),
  ADD CONSTRAINT `bought_ibfk_2` FOREIGN KEY (`id_wydarzenia`) REFERENCES `events` (`id_eventu`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
