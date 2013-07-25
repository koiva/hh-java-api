package ru.yaal.project.hhapi.loader.cache.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public abstract class AbstractStorage implements IStorage {
    protected static final String DATA_FOUND_MESSAGE = "������ ��� url={} � ���� �������. ������: {}.";
    protected static final String DATA_FOUND_BUT_EMPTY_MESSAGE = "������ ��� url={} � ���� �������, �� ��� ������.";
    protected static final String DATA_FOUND_BUT_OUTDATED_MESSAGE = "������ ��� url={} � ���� �������, �� ��� ��������. ������.";
    protected static final String DATA_NOT_FOUND_MESSAGE = "������ ��� url={} ���� �� �������.";
    protected static final String SAVE_DATA_MESSAGE = "�������� � ��� ������ ��� url={} (����� �������� {}).";
    private static final Logger LOG = LoggerFactory.getLogger(AbstractStorage.class);
    protected long lifeTimeMilliSec;

    public AbstractStorage(int lifeTimeMin) {
        this.lifeTimeMilliSec = lifeTimeMin * 60 * 1000;
    }

    protected String generateHashFileName(String realFileName) {
        int hashCode = realFileName.hashCode();
        return "cache_" + hashCode + ".json";
    }

    protected boolean isOutdated(long createDate, long lifeTimeMilliSec) {
        long now = new Date().getTime();
        long outdated = createDate + lifeTimeMilliSec;
        return now > outdated;
    }

    protected String verifyContent(String name, String content, long lastModified) {
        if (content != null) {
            if (!isOutdated(lastModified, lifeTimeMilliSec)) {
                LOG.info(DATA_FOUND_MESSAGE, name, content.length());
                if (!content.isEmpty()) {
                    return content;
                } else {
                    LOG.info(DATA_FOUND_BUT_EMPTY_MESSAGE, name);
                    return null;
                }
            } else {
                LOG.info(DATA_FOUND_BUT_OUTDATED_MESSAGE, name);
                delete(name);
                return null;
            }
        } else {
            LOG.info(DATA_NOT_FOUND_MESSAGE, name);
            return null;
        }
    }
}