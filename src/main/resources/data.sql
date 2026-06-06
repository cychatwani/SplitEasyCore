-- Notification Templates
INSERT INTO notification_templates (type, subject, body, channel)
SELECT 'EMAIL_VERIFICATION', 'Verify your email', 'Hi {{firstName}}, please verify your email by clicking: {{link}}', 'EMAIL'
WHERE NOT EXISTS (SELECT 1 FROM notification_templates WHERE type = 'EMAIL_VERIFICATION' AND channel = 'EMAIL');

-- Languages
INSERT INTO languages (code, name, native_name) VALUES ('en', 'English', 'English') ON CONFLICT (code) DO NOTHING;

-- Currencies
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('INR', 'Indian Rupee', '₹', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('USD', 'US Dollar', '$', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('EUR', 'Euro', '€', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('GBP', 'British Pound', '£', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('JPY', 'Japanese Yen', '¥', 0) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('AUD', 'Australian Dollar', 'A$', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('CAD', 'Canadian Dollar', 'C$', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('CHF', 'Swiss Franc', 'CHF', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('CNY', 'Chinese Yuan', '¥', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('SGD', 'Singapore Dollar', 'S$', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('AED', 'UAE Dirham', 'د.إ', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('SAR', 'Saudi Riyal', '﷼', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('THB', 'Thai Baht', '฿', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('MYR', 'Malaysian Ringgit', 'RM', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('IDR', 'Indonesian Rupiah', 'Rp', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('KRW', 'South Korean Won', '₩', 0) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('BRL', 'Brazilian Real', 'R$', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('ZAR', 'South African Rand', 'R', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('NZD', 'New Zealand Dollar', 'NZ$', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('SEK', 'Swedish Krona', 'kr', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('NOK', 'Norwegian Krone', 'kr', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('DKK', 'Danish Krone', 'kr', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('HKD', 'Hong Kong Dollar', 'HK$', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('TWD', 'Taiwan Dollar', 'NT$', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('PKR', 'Pakistani Rupee', '₨', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('BDT', 'Bangladeshi Taka', '৳', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('LKR', 'Sri Lankan Rupee', 'Rs', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('NPR', 'Nepalese Rupee', 'Rs', 2) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('KWD', 'Kuwaiti Dinar', 'د.ك', 3) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('BHD', 'Bahraini Dinar', 'BD', 3) ON CONFLICT (code) DO NOTHING;
INSERT INTO currencies (code, name, symbol, decimal_places) VALUES ('OMR', 'Omani Rial', '﷼', 3) ON CONFLICT (code) DO NOTHING;