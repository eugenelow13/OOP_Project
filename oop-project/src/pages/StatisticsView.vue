<template>
    <div class="container-xl pt-5">
        <h1>Event Statistics</h1>
        <div class="d-flex gx-4 mt-4 justify-space-betweeen">
            <!-- Date picker -->
            <section class="d-flex w-50 justify-content-end">
                <VueDatePicker class="w-50" v-model="date" range :enable-time-picker="false"></VueDatePicker>
                <button class="btn btn-outline-primary ms-1 me-auto" @click.prevent="showAll">Show All</button>
            </section>

            <!-- Export -->
            <section class="d-flex w-50 justify-content-end">
                <select class="form-select w-25 ms-1" v-model="exportDataType">
                    <option value="xlsx">Excel</option>
                    <option value="pdf">PDF</option>
                </select>
                <button class="btn btn-outline-primary ms-1" @click.prevent="download">Download Report</button>
            </section>
        </div>
        <table class="mt-4 table">
            <thead>
                <tr>
                    <th>Event ID</th>
                    <th>Event Name</th>
                    <th>Total Tickets Sold</th>
                    <th>Total Revenue</th>
                    <th>Event Date</th>
                    <th>Customer Attendance</th>
                    <th>Ticket Price</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="eventStatistics in filteredEventsStatistics" :key="eventStatistics.eventId">
                    <td>{{ eventStatistics.eventId }}</td>
                    <td>{{ eventStatistics.eventName }}</td>
                    <td>{{ eventStatistics.totalTicketsSold }}</td>
                    <td>${{ eventStatistics.totalRevenue }}</td>
                    <td>{{ eventStatistics.eventDate.replace("T", " ") }}</td>
                    <td>{{ eventStatistics.customerAttendance }}</td>
                    <td>${{ eventStatistics.ticketPrice }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>


<script>
import { onMounted, ref, computed } from 'vue';
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'

export default {
    name: 'StatisticsView',
    components: { VueDatePicker },
    setup() {
        const allEventsStatistics = ref([]);
        const exportDataType = ref('xlsx');

        const origDate = ref([]);
        const date = ref([]);

        const filteredEventsStatistics = computed(() => {

            if (date.value.length === 0) {
                return allEventsStatistics.value;
            }

            return allEventsStatistics.value.filter(event => {
                const eventDate = new Date(event.eventDate);
                return eventDate >= new Date(date.value[0]) && eventDate <= new Date(date.value[1]);
            });
        })

        onMounted(async () => {
            const response = await fetch('http://localhost:8080/api/events/statistics');
            const result = await response.json().then(data => data.data);

            allEventsStatistics.value = result.toSorted((a, b) => new Date(a.eventDate) - new Date(b.eventDate))

            // set date range
            const earliestDate = new Date(allEventsStatistics.value[0].eventDate);
            const latestDate = new Date(allEventsStatistics.value[allEventsStatistics.value.length - 1].eventDate);

            origDate.value = [earliestDate, latestDate];
            date.value = [...origDate.value]

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

        function showAll() {
            date.value = [...origDate.value];
        }

        return {
            // data
            allEventsStatistics,
            filteredEventsStatistics,
            exportDataType,
            download,
            date,
            showAll
        }
    }
}


</script>
<style></style>