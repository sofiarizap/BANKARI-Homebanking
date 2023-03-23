const {createApp} = Vue;

createApp({
  data(){
    return{
      accounts: [],
      numberOrigin:"",
      numberDestiny:"",
      amount: "",
      description: "",
      cuentaOrigen: "",
      cuentaDestino: "",
      accountsActive: [],
      }
    },
    created() {
      this.loadData()
      console.log(this.accounts)
    },

    methods: {
      loadData() {
        axios.get("/api/clients/current")
            .then(response => {
                this.accounts = response.data.accounts;
                this.accountsActive = this.accounts.filter(account => account.active == true)
                console.log(this.accounts)
            })
            .catch(error => {
                console.log(error)

            })
    },
    newtrans() {
        console.log(this.amount, this.description, this.numberOrigin, this.numberDestiny)
        axios.post("/api/clients/current/transactions", "amount=" + this.amount + "&description=" + this.description + "&transactionDestiny=" + this.numberDestiny + "&transactionOrigin=" + this.numberOrigin, {
                headers: { 'content-type': 'application/x-www-form-urlencoded' }
            })
            .then(res => {
              Swal.fire({
                icon: 'success',
                title:"SUCCESSFUL TRANSFER",
                showClass: {
                  popup: 'animate__animated animate__fadeInDown'
                },
              })
              setTimeout(() => window.location.href = "/web/accounts.html", 2000)
            })
            .catch(error => {
                console.log(error)
                Swal.fire({
                  icon: 'warning',
                  title: error.response.data,
                  showClass: {
                    popup: 'animate__animated animate__fadeInDown'
                  },
                })
            });
    },
    
},
    



}).mount("#app")