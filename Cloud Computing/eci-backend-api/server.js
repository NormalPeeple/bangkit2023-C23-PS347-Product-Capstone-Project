import express from "express"
import db from "./config/db.js"
import router from "./routes/index.js"
import dotenv from "dotenv"

dotenv.config()
const app = express()
try {
   await db.authenticate()
   console.log("Berhasil terhubung dengan database...!")
} catch (error) {
   console.log(error)
}

app.use(express.json())
app.use(router)

app.listen(8080, () => {
   console.log("Server berjalan pada port 8080")
})