const calculateTotal = (listAllObjective, user, codeQ) => {
    
    let jumlahObj = 0;
    let hasil = 0;
    console.log(listAllObjective)
    listAllObjective.forEach(objective => {
        if(objective.approvalStatus.id === 3){
            jumlahObj++;
            console.log(objective.approvalStatus.status)
            
            //hitung progress tiap KR
            let sum = 0;
            if(objective.listKR.length == 0) {
                sum += 0
            } else {
                objective.listKR.forEach(kr => {
                    sum += kr.progress
                });
                sum = sum/objective.listKR.length
            }
            console.log(sum) // sum = progress 1 objective

            hasil += sum;
            console.log(hasil)
        }
        console.log(hasil)
    });
    return (hasil/jumlahObj).toFixed(1);
}

export default calculateTotal;