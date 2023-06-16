import { Sequelize } from "sequelize"

const db =  new Sequelize('sql-eci', 'root', 'bismillah',{
   host: "34.101.64.250",
   dialect: "mysql"
})

export default db