package top.abeille.basic.profile.service.impl;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import top.abeille.basic.profile.dao.AccountInfoDao;
import top.abeille.basic.profile.entity.AccountInfo;
import top.abeille.common.mock.AbstractServiceMock;

import java.util.Optional;

/**
 * description
 *
 * @author liwenqiang 2019/3/28 20:22
 **/
public class AccountInfoServiceImplTest extends AbstractServiceMock {

    @Mock
    private AccountInfoDao accountInfoDao;

    @InjectMocks
    private AccountInfoServiceImpl accountInfoService;

    @Test
    public void getById() {
        AccountInfo account = new AccountInfo();
        account.setId(0L);
        Mockito.when(accountInfoDao.findById(Mockito.anyLong())).thenReturn(Optional.of(account));
        AccountInfo accountInfo = accountInfoService.getById(0L);
        Assert.assertThat(accountInfo, Matchers.notNullValue());
    }
}