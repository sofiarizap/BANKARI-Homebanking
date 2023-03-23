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
        })
        .catch((error)=>{console.log(error)})
      },
      
    cargarAccount() {
        axios.get("/api/clients/current")
          .then(res => {
                this.client = res.data
                console.log(this.client)
                this.destinyAccount = res.data.accounts
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
                  position: 'top-end',
                  icon: 'success',
                  title:"NEW LOAN SUCCES",
                  showClass: {
                    popup: 'animate__animated animate__fadeInDown'
                  },
                })
                setTimeout(() => window.location.href = "/web/accounts.html", 4000)
            })
            .catch(error => {
              Swal.fire({
                position: 'top-end',
                icon: 'warning',
                title: error.response.data,
                showClass: {
                  popup: 'animate__animated animate__fadeInDown'
                },
                hideClass: {
                  popup: 'animate__animated animate__fadeOutUp'
                }
              })
                console.log(this.amount,this.cargarLoans, this.payments, this.numberAccount)
            });

    },
    
},

}).mount("#app")