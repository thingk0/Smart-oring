import {
  Button,
  FormControl,
  Grid,
  Input,
  MenuItem,
  Select,
  Slider,
  Switch,
} from '@mui/material';
import useGraphicsQualityStore, {
  lowOption,
  mediumOption,
  highOption,
} from '@shared/store/useGraphicsQualityStore';
import {
  EffectQuality,
  LightQuality,
  RenderingScale,
  ShadowDetail,
} from '@shared/types';
import { useState } from 'react';
import classes from './index.module.css';
import HomeRoundedIcon from '@mui/icons-material/HomeRounded';
import { Link } from 'react-router-dom';

export function SettingPage() {
  const {
    graphicsQuality,
    fov,
    shadowDetail,
    renderingScale,
    effectQuality,
    ambientOcclusion,
    lightQuality,
    actions: { setOption },
  } = useGraphicsQualityStore();
  const [optionState, setOptionState] = useState({
    graphicsQuality: graphicsQuality,
    fov: fov,
    shadowDetail: shadowDetail,
    renderingScale: renderingScale,
    effectQuality: effectQuality,
    ambientOcclusion: ambientOcclusion,
    lightQuality: lightQuality,
  });

  const handleBlur = () => {
    if (optionState.fov < 0) {
      setOptionState(prev => {
        return { ...prev, fov: 0 };
      });
    } else if (optionState.fov > 100) {
      setOptionState(prev => {
        return { ...prev, fov: 100 };
      });
    }
  };

  return (
    <main className={classes.main}>
      <header>
        <Link to="/">
          <HomeRoundedIcon fontSize="large" />
        </Link>
        <h1>환경설정</h1>
      </header>
      <h2>그래픽</h2>
      <div>
        <h3>고급 설정</h3>
        <ul>
          <li>
            <div>전반적인 그래픽 품질</div>
            <div>
              <FormControl sx={{ m: 1, minWidth: 120 }} size="small">
                <Select
                  value={optionState.graphicsQuality}
                  onChange={e => {
                    if (e.target.value === 'low') {
                      setOptionState(lowOption);
                    } else if (e.target.value === 'medium') {
                      setOptionState(mediumOption);
                    } else if (e.target.value === 'high')
                      setOptionState(highOption);
                  }}
                  displayEmpty
                  inputProps={{ 'aria-label': 'Without label' }}
                >
                  <MenuItem value={'low'}>낮음</MenuItem>
                  <MenuItem value={'medium'}>보통</MenuItem>
                  <MenuItem value={'high'}>높음</MenuItem>
                </Select>
              </FormControl>
            </div>
          </li>
          <li>
            <div>렌더링 크기</div>
            <div>
              <FormControl sx={{ m: 1, minWidth: 120 }} size="small">
                <Select
                  value={optionState.renderingScale}
                  onChange={e =>
                    setOptionState(prev => {
                      return {
                        ...prev,
                        renderingScale: e.target.value as RenderingScale,
                      };
                    })
                  }
                  displayEmpty
                  inputProps={{ 'aria-label': 'Without label' }}
                >
                  <MenuItem value={0.5}>50%</MenuItem>
                  <MenuItem value={0.75}>75%</MenuItem>
                  <MenuItem value={1}>100%</MenuItem>
                  <MenuItem value={1.25}>125%</MenuItem>
                  <MenuItem value={1.5}>150%</MenuItem>
                  <MenuItem value={2}>200%</MenuItem>
                  <MenuItem value={'auto'}>자동</MenuItem>
                </Select>
              </FormControl>
            </div>
          </li>
          <li>
            <div>카메라 시야 FOV</div>
            <div className={classes.fovslider}>
              <Grid container spacing={2} alignItems="center">
                <Grid item style={{ flex: 1 }}>
                  <Slider
                    value={
                      typeof optionState.fov === 'number' ? optionState.fov : 0
                    }
                    onChange={(_, newValue) =>
                      setOptionState(prev => {
                        return { ...prev, fov: newValue as number };
                      })
                    }
                    aria-labelledby="input-slider"
                    style={{ width: '100%' }}
                  />
                </Grid>
                <Grid item style={{ flex: 0.3 }}>
                  <Input
                    value={optionState.fov}
                    size="small"
                    onChange={e =>
                      setOptionState(prev => {
                        return { ...prev, fov: Number(e.target.value) };
                      })
                    }
                    onBlur={handleBlur}
                    inputProps={{
                      step: 10,
                      min: 0,
                      max: 100,
                      type: 'number',
                      'aria-labelledby': 'input-slider',
                    }}
                  />
                </Grid>
              </Grid>
            </div>
          </li>

          <li>
            <div>그림자</div>
            <FormControl sx={{ m: 1, minWidth: 120 }} size="small">
              <Select
                value={optionState.shadowDetail}
                onChange={e =>
                  setOptionState(prev => {
                    return {
                      ...prev,
                      shadowDetail: e.target.value as ShadowDetail,
                    };
                  })
                }
                displayEmpty
                inputProps={{ 'aria-label': 'Without label' }}
              >
                <MenuItem value={'off'}>끄기</MenuItem>
                <MenuItem value={'low'}>낮음</MenuItem>
                <MenuItem value={'high'}>높음</MenuItem>
              </Select>
            </FormControl>
          </li>
          <li>
            <div>앰비언트 오클루전</div>
            <div>
              <Switch
                checked={optionState.ambientOcclusion}
                onChange={e =>
                  setOptionState(prev => {
                    return { ...prev, ambientOcclusion: e.target.checked };
                  })
                }
              />
            </div>
          </li>
          <li>
            <div>조명 품질</div>
            <div>
              <FormControl sx={{ m: 1, minWidth: 120 }} size="small">
                <Select
                  value={optionState.lightQuality}
                  onChange={e =>
                    setOptionState(prev => {
                      return {
                        ...prev,
                        lightQuality: e.target.value as LightQuality,
                      };
                    })
                  }
                  displayEmpty
                  inputProps={{ 'aria-label': 'Without label' }}
                >
                  <MenuItem value={'low'}>낮음</MenuItem>
                  <MenuItem value={'medium'}>보통</MenuItem>
                  <MenuItem value={'high'}>높음</MenuItem>
                </Select>
              </FormControl>
            </div>
          </li>
          <li>
            <div>이펙트</div>
            <div>
              <FormControl sx={{ m: 1, minWidth: 120 }} size="small">
                <Select
                  value={optionState.effectQuality}
                  onChange={e =>
                    setOptionState(prev => {
                      return {
                        ...prev,
                        effectQuality: e.target.value as EffectQuality,
                      };
                    })
                  }
                  displayEmpty
                  inputProps={{ 'aria-label': 'Without label' }}
                >
                  <MenuItem value={'low'}>낮음</MenuItem>
                  <MenuItem value={'high'}>높음</MenuItem>
                </Select>
              </FormControl>
            </div>
          </li>
        </ul>
        <div>
          <Grid
            container
            spacing={2}
            justifyContent="flex-end"
            alignItems="flex-end"
            style={{ position: 'fixed', bottom: 20, right: 20 }}
          >
            <Grid item>
              <Button
                variant="outlined"
                onClick={() => setOptionState(mediumOption)}
              >
                초기화
              </Button>
            </Grid>
            <Grid item>
              <Button
                variant="contained"
                onClick={() => setOption(optionState)}
              >
                저장
              </Button>
            </Grid>
          </Grid>
        </div>
      </div>
    </main>
  );
}
