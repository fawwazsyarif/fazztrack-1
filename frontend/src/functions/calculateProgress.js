const calculateProgress = (value) => {

    let sum = 0;
    if(value.length == 0) {
        return sum
    } else {
        value.forEach(element => {
            sum += element.progress
        });
    }
    return (sum/value.length).toFixed(1)
}

export default calculateProgress;