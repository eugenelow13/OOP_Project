<template>
    <div class="container-xl pt-5">
        <h1>Event Statistics</h1>
        <table class="mt-4 table">
            <thead>
                <tr>
                    <th>Event Name</th>
                    <th>Event ID</th>
                    <th>Total Tickets Sold</th>
                    <th>Total Revenue</th>
                    <th>Event Date</th>
                    <th>Customer Attendance</th>
                    <th>Ticket Price</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="eventStatistics in allEventsStatistics" :key="eventStatistics.eventId">
                    <td>{{ eventStatistics.eventName }}</td>
                    <td>{{ eventStatistics.eventId }}</td>
                    <td>{{ eventStatistics.totalTicketsSold }}</td>
                    <td>${{ eventStatistics.totalRevenue }}</td>
                    <td>{{ eventStatistics.eventDate }}</td>
                    <td>{{ eventStatistics.customerAttendance }}</td>
                    <td>${{ eventStatistics.ticketPrice }}</td>
                </tr>
            </tbody>
        </table>
        <div class="d-flex gx-4 justify-content-end">
            <select class="form-select w-25" v-model="exportDataType">
                <option value="xlsx">Excel</option>
                <option value="pdf">PDF</option>
            </select>
            <button class="btn btn-outline-primary ms-1" @click.prevent="download">Download</button>
        </div>
    </div>
</template>


<script>
import { onMounted, ref } from 'vue';

export default {
    name: 'StatisticsView',
    setup() {
        const allEventsStatistics = ref([]);
        const exportDataType = ref('xlsx');

        onMounted(async () => {
            const response = await fetch('http://localhost:8080/api/events/statistics');
            allEventsStatistics.value = await response.json().then(data => data.data);
        });

        async function download() {
            const response = await fetch(`http://localhost:8080/api/events/statistics/export?type=${exportDataType.value}`);
            const blob = await response.blob();
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = "event-statistics";
            a.click();
        }

        return {
            // data
            allEventsStatistics,
            exportDataType,
            download
        }
    }
}


</script>
<style></style>