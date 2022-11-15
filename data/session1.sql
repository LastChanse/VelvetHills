-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1
-- Время создания: Ноя 15 2022 г., 19:32
-- Версия сервера: 10.4.14-MariaDB
-- Версия PHP: 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `session1`
--

-- --------------------------------------------------------

--
-- Структура таблицы `dolshnosts`
--

CREATE TABLE `dolshnosts` (
  `idDolshnost` int(11) NOT NULL,
  `NameDolshnost` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `dolshnosts`
--

INSERT INTO `dolshnosts` (`idDolshnost`, `NameDolshnost`) VALUES
(1, 'Администратор'),
(2, 'Продавец'),
(3, 'Старший смены');

-- --------------------------------------------------------

--
-- Структура таблицы `klients`
--

CREATE TABLE `klients` (
  `idKlient` int(11) NOT NULL,
  `FIO` text NOT NULL,
  `Pasport` text NOT NULL,
  `DateBitrth` date NOT NULL,
  `Adress` text NOT NULL,
  `Email` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `sotrudnics`
--

CREATE TABLE `sotrudnics` (
  `idSotrudnic` int(11) NOT NULL,
  `FIO` text NOT NULL,
  `Login` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `Dolshnosts_idDolshnost` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `sotrudnics`
--

INSERT INTO `sotrudnics` (`idSotrudnic`, `FIO`, `Login`, `Password`, `Dolshnosts_idDolshnost`) VALUES
(101, 'Иванов Иван Иванович', 'Ivanov@namecomp.ru', '2L6KZG', 2),
(102, 'Петров Петр Петрович', 'petrov@namecomp.ru', 'uzWC67', 2),
(103, 'Федоров Федор Федорович', 'fedorov@namecomp.ru', '8ntwUp', 1),
(104, 'Миронов Вениамин Куприянович', 'mironov@namecomp.ru', 'YOyhfR', 3),
(105, 'Ширяев Ермолай Вениаминович', 'shiryev@namecomp.ru', 'RSbvHv', 3),
(106, 'Игнатов Кассиан Васильевич', 'ignatov@namecomp.ru', 'rwVDh9', 3),
(107, 'Хохлов Владимир Мэлсович', 'hohlov@namecomp.ru', 'LdNyos', 2),
(108, 'Стрелков Мстислав Георгьевич', 'strelkov@namecomp.ru', 'gynQMT', 2),
(109, 'Беляева Лилия Наумовна', 'belyeva@@namecomp.ru', 'AtnDjr', 2),
(110, 'Смирнова Ульяна Гордеевна', 'smirnova@@namecomp.ru', 'JlFRCZ', 2);

-- --------------------------------------------------------

--
-- Структура таблицы `stasuszacaza`
--

CREATE TABLE `stasuszacaza` (
  `idStasusZacaza` int(11) NOT NULL,
  `StatusName` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `uslugs`
--

CREATE TABLE `uslugs` (
  `idUslugi` int(11) NOT NULL,
  `Name` text NOT NULL,
  `CodeUslugi` varchar(45) NOT NULL,
  `Cena` decimal(6,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `vxod`
--

CREATE TABLE `vxod` (
  `idVxod` int(11) NOT NULL,
  `DateTime` datetime NOT NULL,
  `TypeVxod` varchar(50) NOT NULL,
  `sotrudnics_idSotrudnic` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `vxod`
--

INSERT INTO `vxod` (`idVxod`, `DateTime`, `TypeVxod`, `sotrudnics_idSotrudnic`) VALUES
(1, '2022-05-15 13:13:00', 'Успешно', 101),
(2, '2022-05-15 13:13:00', 'Успешно', 102),
(3, '2022-05-15 13:13:00', 'Успешно', 103),
(4, '2022-05-15 13:13:00', 'Успешно', 104),
(5, '2022-05-15 13:13:00', 'Неуспешно', 105),
(6, '2022-05-15 13:13:00', 'Успешно', 106),
(7, '2022-05-15 13:13:00', 'Успешно', 107),
(8, '2022-05-15 13:13:00', 'Неуспешно', 108),
(9, '2022-05-15 13:13:00', 'Успешно', 109),
(10, '2022-05-15 13:13:00', 'Успешно', 110);

-- --------------------------------------------------------

--
-- Структура таблицы `zacazs`
--

CREATE TABLE `zacazs` (
  `idZacaz` int(11) NOT NULL,
  `CodeZacaza` varchar(45) NOT NULL,
  `DateCreate` date NOT NULL,
  `TimeZacaz` time NOT NULL,
  `DateClose` date DEFAULT NULL,
  `TimeProcat` time DEFAULT NULL,
  `Klients_idKlient` int(11) NOT NULL,
  `StasusZacaza_idStasusZacaza` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `zacazs_has_uslugs`
--

CREATE TABLE `zacazs_has_uslugs` (
  `Zacazs_idZacaz` int(11) NOT NULL,
  `Uslugs_idUslugi` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `dolshnosts`
--
ALTER TABLE `dolshnosts`
  ADD PRIMARY KEY (`idDolshnost`);

--
-- Индексы таблицы `klients`
--
ALTER TABLE `klients`
  ADD PRIMARY KEY (`idKlient`);

--
-- Индексы таблицы `sotrudnics`
--
ALTER TABLE `sotrudnics`
  ADD PRIMARY KEY (`idSotrudnic`),
  ADD KEY `fk_Sotrudnics_Dolshnosts1_idx` (`Dolshnosts_idDolshnost`);

--
-- Индексы таблицы `stasuszacaza`
--
ALTER TABLE `stasuszacaza`
  ADD PRIMARY KEY (`idStasusZacaza`);

--
-- Индексы таблицы `uslugs`
--
ALTER TABLE `uslugs`
  ADD PRIMARY KEY (`idUslugi`);

--
-- Индексы таблицы `vxod`
--
ALTER TABLE `vxod`
  ADD PRIMARY KEY (`idVxod`),
  ADD KEY `fk_vxod_sotrudnics1_idx` (`sotrudnics_idSotrudnic`);

--
-- Индексы таблицы `zacazs`
--
ALTER TABLE `zacazs`
  ADD PRIMARY KEY (`idZacaz`),
  ADD KEY `fk_Zacazs_Klients1_idx` (`Klients_idKlient`),
  ADD KEY `fk_Zacazs_StasusZacaza1_idx` (`StasusZacaza_idStasusZacaza`);

--
-- Индексы таблицы `zacazs_has_uslugs`
--
ALTER TABLE `zacazs_has_uslugs`
  ADD PRIMARY KEY (`Zacazs_idZacaz`,`Uslugs_idUslugi`),
  ADD KEY `fk_Zacazs_has_Uslugs_Uslugs1_idx` (`Uslugs_idUslugi`),
  ADD KEY `fk_Zacazs_has_Uslugs_Zacazs_idx` (`Zacazs_idZacaz`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `dolshnosts`
--
ALTER TABLE `dolshnosts`
  MODIFY `idDolshnost` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT для таблицы `klients`
--
ALTER TABLE `klients`
  MODIFY `idKlient` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45462596;

--
-- AUTO_INCREMENT для таблицы `sotrudnics`
--
ALTER TABLE `sotrudnics`
  MODIFY `idSotrudnic` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=112;

--
-- AUTO_INCREMENT для таблицы `stasuszacaza`
--
ALTER TABLE `stasuszacaza`
  MODIFY `idStasusZacaza` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT для таблицы `uslugs`
--
ALTER TABLE `uslugs`
  MODIFY `idUslugi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=354;

--
-- AUTO_INCREMENT для таблицы `vxod`
--
ALTER TABLE `vxod`
  MODIFY `idVxod` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT для таблицы `zacazs`
--
ALTER TABLE `zacazs`
  MODIFY `idZacaz` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `sotrudnics`
--
ALTER TABLE `sotrudnics`
  ADD CONSTRAINT `fk_Sotrudnics_Dolshnosts1` FOREIGN KEY (`Dolshnosts_idDolshnost`) REFERENCES `dolshnosts` (`idDolshnost`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ограничения внешнего ключа таблицы `vxod`
--
ALTER TABLE `vxod`
  ADD CONSTRAINT `fk_vxod_sotrudnics1` FOREIGN KEY (`sotrudnics_idSotrudnic`) REFERENCES `sotrudnics` (`idSotrudnic`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ограничения внешнего ключа таблицы `zacazs`
--
ALTER TABLE `zacazs`
  ADD CONSTRAINT `fk_Zacazs_Klients1` FOREIGN KEY (`Klients_idKlient`) REFERENCES `klients` (`idKlient`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Zacazs_StasusZacaza1` FOREIGN KEY (`StasusZacaza_idStasusZacaza`) REFERENCES `stasuszacaza` (`idStasusZacaza`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ограничения внешнего ключа таблицы `zacazs_has_uslugs`
--
ALTER TABLE `zacazs_has_uslugs`
  ADD CONSTRAINT `fk_Zacazs_has_Uslugs_Uslugs1` FOREIGN KEY (`Uslugs_idUslugi`) REFERENCES `uslugs` (`idUslugi`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Zacazs_has_Uslugs_Zacazs` FOREIGN KEY (`Zacazs_idZacaz`) REFERENCES `zacazs` (`idZacaz`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;