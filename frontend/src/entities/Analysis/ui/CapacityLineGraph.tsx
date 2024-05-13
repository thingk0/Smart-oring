import ReactApexCharts from 'react-apexcharts';

const option = {};

type CapacityLineGraphProps = {
  yesterday: Array<number>;
  today: Array<number>;
};

function CapacityLineGraph({ yesterday, today }: CapacityLineGraphProps) {
  const series: any[] = [
    {
      name: 'Yesterday',
      data: yesterday,
    },
    {
      name: 'Today',
      data: today,
    },
  ];

  return (
    <div>
      <div>미션 처리량</div>
      <ReactApexCharts type="line" series={series} options={option} />
    </div>
  );
}

export default CapacityLineGraph;
