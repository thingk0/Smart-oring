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

type RadialBarProps = {
  data: number;
};

function RadialBar({ data }: RadialBarProps) {
  return <ReactApexChart type="radialBar" series={[data]} options={option} />;
}
export default RadialBar;
