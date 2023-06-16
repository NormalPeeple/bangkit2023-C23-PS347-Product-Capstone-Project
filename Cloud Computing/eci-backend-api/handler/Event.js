//import { error } from "console";
import db from "../config/db.js";
import addEvent from "../models/ModelEvent.js";
import fs from "fs";
import axios from "axios";
import { createConnection } from 'mysql';
import bodyParser from "body-parser";

const parse = bodyParser.urlencoded({extended:true})

// get all event
export const getEvent = [parse, async(req, res) => {
   try {
      const event = await addEvent.findAll()
      res.json(event)
   } catch (error) {
      console.log(error)
   }
}]

// add event
export const createPengajuanEvent = [parse, async (req, res) => {
  try {
    // Membuat koneksi ke database
    const connection = createConnection({
      host: '34.101.64.250',
      user: 'root',
      password: 'bismillah',
      database: 'sql-eci'
    });

    const { nama_event, jenis_event, cara_transaksi, tgl_mulai, tgl_berakhir, deskripsi } = req.body;
    const fotoBuffer = fs.readFileSync(req.file.path);
    const url = 'https://model-ml-py2-c6fl6h5vlq-et.a.run.app/predict';

    // Query SQL untuk mengambil data penyelenggara dan kota_asal_penyelenggara
    const query = 'SELECT penyelenggara, kota_asal_penyelenggara FROM data_pengguna';

    // Menjalankan query
    connection.query(query, async (error, results, fields) => {
      if (error) {
        console.error('Kesalahan query: ' + error.stack);
        return;
      }

      // Ambil data penyelenggara dan kota_asal_penyelenggara dari hasil query
      const { penyelenggara, kota_asal_penyelenggara } = results[1, 3];

      // Buat objek dataMl dengan data yang diperlukan untuk input ke model ML
      const dataMl = {
        penyelenggara: penyelenggara,
        kota_asal_penyelenggara: kota_asal_penyelenggara,
        jenis_event: jenis_event,
        cara_transaksi: cara_transaksi
      };

      try {
        // Kirim dataMl ke model ML
        const response = await axios.post(url, dataMl);
        const hasilModelMl = response.data

        // Simpan pengajuan event ke database tabel pengajuan_event
        const newPengajuanEvent = {
          nama_event: nama_event,
          jenis_event: jenis_event,
          cara_transaksi: cara_transaksi,
          tgl_mulai: tgl_mulai,
          tgl_berakhir: tgl_berakhir,
          deskripsi: deskripsi,
          foto: fotoBuffer
        };
        await addEvent.create(newPengajuanEvent);

        // Kirim respons dengan hasil pengolahan model ML dan pesan sukses
        res.status(200).json({
          isFraudData: hasilModelMl.isFraud,
          message: 'Pengajuan berhasil diajukan'
        });
      } catch (error) {
        console.error('Terjadi kesalahan saat mengirim ke model ML:', error);
        res.status(500).json({ message: 'Terjadi kesalahan saat mengirim ke model ML' });
      }
    });
  } catch (error) {
    console.error('Terjadi kesalahan saat membuat pengajuan event:', error);
    res.status(500).json({ message: 'Gagal membuat pengajuan event' });
  }
}];


// delete event
export const hapusEventById = [parse, async (req, res) => {
   const { id } = req.params;
 
   try {
     const deleteEvent = await addEvent.findByPk(id);
 
     if (!deleteEvent) {
       return res.status(404).json({ message: 'Event tidak ditemukan...!' });
     }
 
     await deleteEvent.destroy();
 
     return res.status(200).json({ message: 'Event berhasil dihapus...!' });
   } catch (error) {
     console.error(error);
     return res.status(500).json({ message: 'Terjadi kesalahan saat menghapus event...!' });
   }
 }];

// detail event
export const detailEvent = [parse, (req, res) => {
   const id = req.params.id
   const sql = `SELECT * FROM pengajuan_event WHERE id = ${id}`;

   db.query(sql, (err, result) => {
      if (err) {
         throw err;
      }
      if (result.length === 0) {
         res.status(404).json({ message: 'Event tidak ditemukan' });
      } else {
         res.status(200).json(result[0]);
      }
   })
 }]

 // update event
export const updateEventById = [parse, async (req, res) => {
   const { id } = req.params;
   const { nama_event, jenis_event, cara_transaksi, tgl_mulai, tgl_berakhir, deskripsi } = req.body;
   const fotoBuffer = fs.readFileSync(req.file.path);
 
     if (req.file.size > 1 * 1024 * 1024) {
       // Jika ukuran file foto melebihi 1 MB, kembalikan respons kesalahan
       fs.unlinkSync(req.file.path); // Hapus file foto yang diunggah
       return res.status(400).json({ message: 'Ukuran file foto melebihi batasan maksimal (1 MB)' });
     }
 
   try {
     const ubahEvent = await addEvent.findByPk(id);
 
     if (!ubahEvent) {
       return res.status(404).json({ message: 'Event tidak ditemukan...!' });
     }
 
     ubahEvent.nama_event = nama_event
     ubahEvent.jenis_event = jenis_event
     ubahEvent.cara_transaksi = cara_transaksi
     ubahEvent.tgl_mulai = tgl_mulai
     ubahEvent.tgl_berakhir = tgl_berakhir
     ubahEvent.deskripsi = deskripsi
     ubahEvent.foto = fotoBuffer
 
     await ubahEvent.save();
 
     return res.status(200).json({ message: 'Event berhasil diubah...!' });
   } catch (error) {
     console.error(error);
     return res.status(500).json({ message: 'Gagal mengubah event...!' });
   }
 }]