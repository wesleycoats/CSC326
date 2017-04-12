INSERT INTO ndcodes(Code, Description) VALUES
('00904-2407','Tetracycline'),
('08109-6','Aspirin'),
('64764-1512','Prioglitazone'),
('54868-4985', 'Citalopram Hydrobromide'),
('00060-431', 'Benzoyl Peroxide'),
('67877-1191','Ibuprofen'),
('0338-1021-41', 'Penicillin'),
('0440-7026-30', 'Acetaminophen And Codeine'),
('17856-0007-1', 'Sulfatrim')

ON DUPLICATE KEY UPDATE Code = Code;
