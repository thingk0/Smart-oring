import ReactApexChart from 'react-apexcharts';

const option: any = {
  labels: ['가동률'],
  dataLabels: {
    enabled: false,
  },
  plotOptions: {
    radialBar: {
      startAngle: -150,
      endAngle: 150,
    },
  },
};

type OperatingRateProps = {
  data: number;
};

function OperatingRate({ data }: OperatingRateProps) {
  return (
    <div>
      <h3>가동률</h3>
      <ReactApexChart type="radialBar" series={[data]} options={option} />
    </div>
  );
}
export default OperatingRate;
