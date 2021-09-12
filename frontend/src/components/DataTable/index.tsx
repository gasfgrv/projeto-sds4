import axios from "axios";
import Pagination from "components/Pagination";
import { useEffect, useState } from "react";
import { SalePage } from "types/sale";
import { formatLocalDate } from "utils/format";
import { BASE_URL } from "utils/requests";

const DataTable = () => {

    const [activePage, setActivePage] = useState(0);
    const [page, setPage] = useState<SalePage>({
        first: true,
        last: true,
        number: 0,
        totalElements: 0,
        totalPages: 0
    });

    useEffect(() => {
        axios.get(`${BASE_URL}/sales?page=${activePage}&size=20&sort=date,desc`)
            .then(res => {
                setPage(res.data);
            });
    }, [activePage]);

    const changepage = (index: number) => {
        setActivePage(index);
    }

    return (
        <>
            <Pagination 
                page={page}
                onPageChange = {changepage}
            />
            <div className="table-responsive">
                <table className="table table-striped table-sm">
                    <thead>
                        <tr>
                            <th>Data</th>
                            <th>Vendedor</th>
                            <th>Clientes visitados</th>
                            <th>Negócios fechados</th>
                            <th>Valor</th>
                        </tr>
                    </thead>
                    <tbody>
                        {page.content?.map(s => (
                            <tr key={s.id}>
                                <td>{formatLocalDate(s.date, 'dd/MM/yyyy')}</td>
                                <td>{s.seller.name}</td>
                                <td>{s.visited}</td>
                                <td>{s.deals}</td>
                                <td>${s.amount.toFixed(2).replace('.', ',')}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </>
    )
}

export default DataTable;