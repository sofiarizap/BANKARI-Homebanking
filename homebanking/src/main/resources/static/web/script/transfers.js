const {createApp} = Vue;

createApp({
  data(){
    return{
      accounts: [],
      numberOrigin:"",
      numberDestiny:"",
      amount: 0,
      description: "",
      cuentaOrigen: "",
      cuentaDestino: "",
      }
    },
    created() {
      this.loadData()
      console.log(this.accounts)
    },

    methods: {
      loadData() {
        axios.get("http://localhost:8080/api/clients/current")
            .then(response => {
                this.accounts = response.data.accounts;
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
                position: 'top-end',
                icon: 'success',
                title:"SUCCES TRANSFER",
                showClass: {
                  popup: 'animate__animated animate__fadeInDown'
                },
                hideClass: {
                  popup: 'animate__animated animate__fadeOutUp'
                }
              })
              setTimeout(() => window.location.href = "/web/accounts.html", 4000)
                console.log(this.accounts.transactions)
            })
            .catch(error => {
                console.log(error)
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
            });
    },
    
},
    



}).mount("#app")