# generate_qrcodes.py
import qrcode
import os

INPUT_FILE = './URL.txt'
OUTPUT_DIR = '/Users/csld/NetBeansProjects/OOP2025_threater/src/Qrcode/random'

os.makedirs(OUTPUT_DIR, exist_ok=True)

with open(INPUT_FILE, 'r', encoding='utf-8') as f:
    urls = [line.strip() for line in f if line.strip()]

for i, url in enumerate(urls):
    img = qrcode.make(url)
    img.save(f'{OUTPUT_DIR}/qr_{i+1}.png')

print(f'Generated {len(urls)} QR codes in {OUTPUT_DIR}')