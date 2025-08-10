# Smart-Parking-Bill-System-
Smart Parking Bill System – An AI-powered parking management solution using ANPR, YOLO, and OCR for automatic license plate detection, integrated with Google Firebase for real-time data management, providing a faster, safer, and more efficient parking experience

📌 Features
- **Automatic Number Plate Recognition (ANPR)** using YOLO and PyTesseract  
- **Seamless entry & exit** without manual intervention  
- **Real-time data management** with Google Firebase  
- **Secure payment calculation** based on parking duration  
- **Cloud-based storage** for parking logs and transactions  

 🛠 Tools & Technologies Used
- **Operating System**: Linux/Unix  
- **Backend**: Python (Flask)  
- **Frontend**: HTML, CSS, JavaScript  
- **Number Plate Detection**: YOLO (You Only Look Once)  
- **OCR**: PyTesseract  
- **Database**: MySQL/PostgreSQL  
- **Cloud & Realtime DB**: Google Firebase  
- **Mapping**: Google Maps API  

## 🚀 How It Works
1. Vehicle enters → License plate detected via YOLO.  
2. Plate text extracted using PyTesseract.  
3. Entry time, plate number, and parking slot stored in Firebase.  
4. On exit, duration calculated and bill generated automatically.  
