import express from "express"
import { getPengguna, Register, Login } from "../handler/Pengguna.js"
import { getEvent, detailEvent, createPengajuanEvent, hapusEventById, updateEventById } from "../handler/Event.js"
import { getDonasi, DonasiMasuk } from "../handler/Donasi.js"
import multer, { diskStorage } from 'multer';

const router = express.Router()

router.get("/", (req, res) => {
   res.send("<h1>Home Page</h1>")
})

router.get('/pengguna', getPengguna)
router.post('/pengguna', Register)

router.post('/login', Login)

router.get('/donasi', getDonasi)
router.post('/donasi', DonasiMasuk)

router.get('/event', getEvent)
router.get('/event/:id', detailEvent)

const storage = diskStorage({
   destination: (req, file, cb) => {
     cb(null, 'uploads/'); // Menyimpan file foto di folder 'uploads'
   },
   filename: (req, file, cb) => {
     cb(null, Date.now() + '-' + file.originalname); // Menggunakan timestamp untuk memberikan nama unik pada file foto
   }
 });
 
 const upload = multer({
   storage: storage,
   limits: { fileSize: 1 * 1024 * 1024 } // Batasan ukuran file foto maksimal 1 MB
 });
 
 // Menangani permintaan POST untuk membuat pengajuan event
 router.post('/event', upload.single('foto'), createPengajuanEvent);

 router.delete('/event/:id', hapusEventById)

 router.put('/event/:id', upload.single('foto'), updateEventById)

export default router