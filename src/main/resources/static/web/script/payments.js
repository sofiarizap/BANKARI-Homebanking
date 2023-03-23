const{createApp} = Vue; 

createApp({
  data(){
    return{
      number: "",
      amount: 0,
      cvv: 0,
      description: "",
      account: ""
      
    }
  },
  methods:{
    
    makeTransaction: function() {
      console.log(this.number)
      console.log(this.cvv)
      axios.post("/api/payment",{"number": this.number,"cvv":this.cvv,"amount": this.amount,
      "description": this.description,"account": this.account},
      )
      .then(res => {
        Swal.fire({
          icon: 'success',
          title: 'PAYMENT SUCCESSFUL',
          showClass: {
            popup: 'animate__animated animate__fadeInDown'
          },
        })
          }).catch(error => {
            "error"
            Swal.fire({
              icon:'warning',
              title: error.response.data,
              showClass: {
                popup: 'animate__animated animate__fadeInDown'
              },
            })
        });
  }


}
}).mount("#app")
