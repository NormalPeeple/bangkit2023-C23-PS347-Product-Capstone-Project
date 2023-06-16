import Donasi from "../models/ModelDonasi.js"
import bodyParser from "body-parser"

const bodyP = bodyParser.urlencoded({extended:true});

export const getDonasi = [bodyP, async(req, res) => {
   try {
      const donasi = await Donasi.findAll()
      res.json(donasi)
   } catch (error) {
      console.log(error)
   }
}]

export const DonasiMasuk =[bodyP, async(req, res) => {
   const jumlah_donasi = req.field
   try {
      await Donasi.create({
         jumlah_donasi: jumlah_donasi
      })
      res.json({
         message: "Terima kasih atas partisipasinya, semoga apa yang diberikan bisa diganti dengan berlipat ganda :)"
      })
   } catch (error) {
      console.log(error)
   }
}]
