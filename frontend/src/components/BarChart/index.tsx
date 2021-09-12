import axios from 'axios';
import { type } from 'os';
import { useEffect, useState } from 'react';
import Chart from 'react-apexcharts';
import { SaleSuccess } from 'types/sale';
import { round } from 'utils/format';
import { BASE_URL } from 'utils/requests';

type SeriesData = {
    name: string;
    data: number[];
}

type ChartData = {
    labels: {
        categories: string[]
    };
    series: SeriesData[];
}

const BarChart = () => {

    const [ chartData, setChartData ] = useState<ChartData>({
        labels:{
            categories: []
        },
        series: [
            {
                name: "",
                data: []
            }
        ]
    });

    useEffect(() => {
        axios.get(`${BASE_URL}/sales/success-by-seller`)
        .then(res => {
            const data = res.data as SaleSuccess[];
            const labels = data.map(s => s.sellerName);
            const series = data.map(s => round(100 * (s.deals / s.visited), 1) );

            setChartData({
                labels:{
                    categories: labels
                },
                series: [
                    {
                        name: "% de sucesso",
                        data: series
                    }
                ]
            });
        });
    }, []);

    const options = {
        plotOptions: {
            bar: {
                horizontal: true,
            }
        },
    };

    return (
        <Chart
            options={{ ...options, xaxis: chartData.labels, }}
            series={chartData.series}
            type="bar"
            height="240"
        />
    );
}

export default BarChart;