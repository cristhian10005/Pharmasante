let miCanva = document.getElementById("graficos").getContext("2d");
var chart = new Chart(miCanva,{
    type: "bar",
    data: {
        labels: ['22-02-2023','23-02-2023','24-02-2023','25-02-2023','22-02-2023'],
        datasets:[{
            label: 'Ganancias',
            data: [325000, 120000, 360000,260000,325000]
        }]
    }
});