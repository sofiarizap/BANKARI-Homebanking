const {createApp} = Vue;

createApp({
  data(){
    return{
        client:"",
        amount: "",
        numberAccount: "",
        destinyAccount: [],
        payments: [],
        loansType: "",
        cargarLoans: [],
        accountsActive: [],
        interesTotal: 0,
        clientLoans: "",

      }
    },
    created() {
      this.cargarAccount()
      this.loadData()
      
    },

    methods: {
      loadData: function(){
        axios.get("/api/loans")
        .then( res =>{
          this.loansType = res.data
        })
        .catch((error)=>{console.log(error)})
      },
      cargardata() {
        axios.get("/api/loans")
        .then( res =>{
          this.payments =  this.loansType.find(loan => loan.name == this.cargarLoans)
          this.payments = this.payments.payments
          this.interest= this.loansType.find(loan => loan.name == this.cargarLoans).interest
        })
        .catch((error)=>{console.log(error)})
      },
      cargarInterest() {
        axios.get("/api/loans")
        .then( res =>{
          this.interesTotal= (((this.amount*this.interest)+parseInt(this.amount))/this.payments).toFixed(2)
          this.paso1=(this.amount*this.interest)+this.amount
        })
        .catch((error)=>{console.log(error)})
      },
      reloadPage(){
        window.location.reload()
      },
      
    cargarAccount() {
        axios.get("/api/clients/current")
          .then(res => {
                this.client = res.data
                console.log(this.client)
                this.destinyAccount = res.data.accounts
                this.accountsActive= this.destinyAccount.filter(account => account.active == true)
                this.clientLoans = this.client.loans.map(name=> name.name)
                console.log(this.clientLoans)
            })
            .catch((error)=>{console.log(error)})
    },

    crearPrestamo() {
        axios.post("/api/clients/current/loans", {"name": this.cargarLoans,"amount": this.amount,
          "payments":this.payments, "destinyAccount": this.numberAccount})
            .then(res => {
                Swal.fire({
                  icon: 'success',
                  title:"NEW LOAN CREATED",
                  showClass: {
                    popup: 'animate__animated animate__fadeInDown'
                  },
                })
                setTimeout(() => window.location.href = "/web/accounts.html", 2000)
            })
            .catch(error => {
              Swal.fire({
                icon: 'warning',
                title: error.response.data,
                showClass: {
                  popup: 'animate__animated animate__fadeInDown'
                },
              })
                console.log(this.amount,this.cargarLoans, this.payments, this.numberAccount)
            });

    },
    
},

}).mount("#app")